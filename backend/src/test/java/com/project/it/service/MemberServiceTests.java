package com.project.it.service;

import com.project.it.domain.Member;
import com.project.it.domain.MemberRole;
import com.project.it.domain.MemberStatus;
import com.project.it.dto.MemberDTO;
import com.project.it.repository.MemberRepository;
import com.project.it.repository.MemberStatusRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MemberStatusService memberStatusService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode() {
        String rawPassword = "12345678";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("===========================================================");
        log.info(result);
    }



    @Test
    public void testRegister(){
        for(int i = 0; i < 100; i++){
        Member member = Member.builder()
                .email("user" + i + "@test.com")
                .pw(passwordEncoder.encode("test"))
                .build();

        memberRepository.save(member);

        member = memberRepository.getWithRoles("user" + i + "@test.com");
        MemberStatus memberS =
                MemberStatus.builder()
                        .mno(member.getMno())
                        .name("user"+i)
                        .birth("100000")
                        .tel("00000000000")
                        .sex("male")
                        .marital_status("미혼")
                        .children_count(0)
                        .qualifications("java")
                        .education("대졸")
                        .antecedents("신입")
                        .member(member)
                        .build();

        memberStatusRepository.save(memberS);
    }}

    @Test
    public void changeBirth() {
        for (int i = 0; i < 100; i++) {
//            Member member = memberRepository.getStatus(952L + i);
//            MemberStatus memberS = memberStatusRepository.getOne(member);
//            member = memberRepository.getStatus(952L + i);
//            member.addRole(MemberRole.INTERN);
//            memberRepository.save(member);
//
//            log.info(memberS);
//
//            memberStatusRepository.save(memberS);
        }
    }

    @Test
    public void read(){
     //Member member = memberRepository.getStatus(952L);
     //MemberStatus memberStatus = memberStatusRepository.getOne(member);
     //log.info(memberStatus);
        }

        @Test
    public void readone(){
        MemberStatus memberStatus = memberStatusRepository.findByMemberMno(952L);
        log.info(memberStatus);
        }

        @Test
    public void list(){
        memberStatusService.list();

        }





}
