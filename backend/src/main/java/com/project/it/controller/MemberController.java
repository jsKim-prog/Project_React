package com.project.it.controller;

import com.project.it.dto.MemberStatusDTO;
import com.project.it.service.MemberStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/it/members")
public class MemberController {

    private final MemberStatusService memberStatusService;

    @PostMapping("/register")
    public Map<String, String> register(MemberStatusDTO R) {
        log.info(R);

        String msg = memberStatusService.register(R);

        return Map.of("RESULT", msg);
    }

    @GetMapping("/{mno}")
    public MemberStatusDTO statusRead(@PathVariable(name = "mno") Long mno){
        log.info(mno);

        MemberStatusDTO memberStatusDTO = memberStatusService.readOne(mno);
        log.info(memberStatusDTO);

        return memberStatusDTO;
    }

    @PostMapping("/modify")
    public Map<String, String> modify(MemberStatusDTO R){
        log.info("modify 내용 " + R);

        String msg = memberStatusService.modifyOne(R);;

        log.info(msg);

        return Map.of("RESULT", msg);

    }

}
