package com.project.it.repository;

import com.project.it.domain.Member;
import com.project.it.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;




    @Test
    public void testInsertMember() {
        Member member = Member.builder()
                .email("user2@test.com")
                .pw(passwordEncoder.encode("test2"))
                .build();

        member.addRole(MemberRole.STAFF);

        memberRepository.save(member);


    }





    @Test
    public void testRead(){
        String email = "user2@test.com";

        Member member = memberRepository.getWithRoles(email);


        log.info(member);


        log.info("------------------------------------");
        log.info(member);
    }

    @Test
    public void testModify(){
        String mno = "1";

        Optional<Member> result = memberRepository.findById(mno);

        Member member = result.orElseThrow();

        member.changePw(passwordEncoder.encode("passwordtest"));

        memberRepository.save(member);
    }

    @Test
    public void testDelete(){
        String mno = "1";

        Optional<Member> result = memberRepository.findById(mno);

        Member member = result.orElseThrow();



        memberRepository.save(member);

    }

}




