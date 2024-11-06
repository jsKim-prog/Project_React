package com.project.it.controller;

import com.project.it.domain.Member;
import com.project.it.domain.MemberRole;
import com.project.it.domain.MemberStatus;
import com.project.it.domain.Organization;
import com.project.it.dto.MemberDTO;
import com.project.it.dto.MemberModifyDTO;
import com.project.it.dto.MemberRegisterDTO;
import com.project.it.repository.MemberRepository;
import com.project.it.repository.MemberStatusRepository;
import com.project.it.repository.OrganizationRepository;
import com.project.it.service.MemberService;
import com.project.it.service.MemberStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.server.Cookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberStatusService memberSService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberStatusRepository memberSRepostiory;
    private final OrganizationRepository organizationRepository;


    @PostMapping("/it/members/register")

    public Map<String, String> register(MemberRegisterDTO R) {
        Member member = Member.builder()
                .email(R.getEmail())
                .pw(passwordEncoder.encode(R.getPw()))
                .build();

        member.addRole(MemberRole.INTERN);

        memberRepository.save(member);
        member = memberRepository.getWithRoles(R.getEmail());
        MemberStatus memberS =
                MemberStatus.builder()
                        .mno(member.getMno())
                        .name(R.getName())
                        .birth(R.getBirth())
                        .tel(R.getTel())
                        .sex(R.getSex())
                        .marital_status(R.getMarital_status())
                        .children_count(R.getChildren_count())
                        .qualifications(R.getQualifications())
                        .education(R.getEducation())
                        .antecedents(R.getAntecedents())
                        .build();


        memberSRepostiory.save(memberS);
        return Map.of("RESULT", member.toString());
    }

    @GetMapping("/it/members/statusRead")
    public Map<String, String> statusRead(int mno){
        Member member = memberRepository.getStatus(mno);
        log.info(member);
        MemberStatus memberStatus = memberSRepostiory.getStatus(mno);
        String result = member.toString()+memberStatus.toString();
        log.info(result);

        return Map.of("RESULT", result);

    }

    @GetMapping("/it/members/list")
    public Map<String, String> memberList(int mno){
        Member member = memberRepository.getStatus(mno);
        MemberStatus memberStatus = memberSRepostiory.getStatus(mno);
        Organization organization = organizationRepository.getStatus(mno);

        log.info(member);
        log.info(memberStatus);
        log.info(organization);
        String result = member.toString()+memberStatus.toString()+organization.toString();
        log.info(result);

        return Map.of("RESULT", result);


    }
}
