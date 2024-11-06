package com.project.it.security.filter;

import com.google.gson.Gson;
import com.project.it.dto.MemberDTO;
import com.project.it.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
       @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws
            ServletException {
        // shoutNotFilter()는 OncePerRequestFilter의 상위 클래스에 정의된 메서드
        // 필터로 체크하지 않을 경로나 메서드(GET/POST) 등을 지정하기 위해 사용

        //Preflight 요청은 체크하지 않음
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        String path = request.getRequestURI();

        log.info("check uri ........." + path);

        //api/member 경로는 호출하지 않음.
        if(path.startsWith("/it/members/")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("---------------------- JWT Check Filter ----------------------");


        String authHeaderStr = request.getHeader("Authorization");

        log.info(authHeaderStr);
        try {
            //Bearer 형식의 accestoken...
            String accessToken = authHeaderStr.substring(7);
            log.info("accessToken : " + accessToken);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info(claims);
            log.info("JWT claims : " + claims);

            //filterChain.doFilter(request, response);//pass
            String email = (String) claims.get("email");
            String pw = (String) claims.get("pw");
            int mno = (int) claims.get("mno");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(email, pw, mno, roleNames);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request,response);







        } catch (Exception e){
            log.error("JWT Check Error..................");
            log.error(e.getMessage());
            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");

            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }


}