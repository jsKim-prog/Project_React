package com.project.it.service;

import com.project.it.domain.Member;
import com.project.it.dto.MemberModifyDTO;
import com.project.it.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

 private final MemberRepository memberRepository;
 private final PasswordEncoder passwordEncoder;

  @Override
 public void modifyMember(MemberModifyDTO memberModifyDTO) {
  Optional<Member> result = memberRepository.findById(memberModifyDTO.getMember().getEmail());

  Member member = result.orElseThrow();

  member.changePw(passwordEncoder.encode(memberModifyDTO.getMember().getPw()));

  memberRepository.save(member);
 }
}