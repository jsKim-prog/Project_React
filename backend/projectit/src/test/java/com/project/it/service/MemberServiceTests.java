package com.project.it.service;

import com.project.it.domain.Member;
import com.project.it.domain.MemberRole;
import com.project.it.domain.MemberStatus;
import com.project.it.dto.MemberDTO;
import com.project.it.repository.MemberRepository;
import com.project.it.repository.MemberStatusRepository;
import lombok.extern.log4j.Log4j2;
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
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister(){
        Member member = Member.builder()
                .email("user3@test.com")
                .pw(passwordEncoder.encode("test3"))
                .build();

        memberRepository.save(member);

        member = memberRepository.getWithRoles("user3@test.com");
        MemberStatus memberS =
                MemberStatus.builder()
                        .mno(member.getMno())
                        .name("user3")
                        .birth("100000")
                        .tel("00000000000")
                        .sex("male")
                        .marital_status("미혼")
                        .children_count(0)
                        .qualifications("java")
                        .education("대졸")
                        .antecedents("신입")
                        .build();


        memberStatusRepository.save(memberS);






    }
}
