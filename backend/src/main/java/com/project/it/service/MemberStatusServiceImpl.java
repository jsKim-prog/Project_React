package com.project.it.service;

import com.project.it.domain.Member;
import com.project.it.domain.MemberRole;
import com.project.it.domain.MemberStatus;
import com.project.it.dto.MemberStatusDTO;
import com.project.it.repository.MemberRepository;
import com.project.it.repository.MemberStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberStatusServiceImpl implements MemberStatusService{

    private final MemberStatusRepository memberSR;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(MemberStatusDTO R) {
        Member member = Member.builder()
                .email(R.getEmail())
                .pw(passwordEncoder.encode(R.getPassword()))
                .build();

        member.addRole(MemberRole.INTERN);

        log.info(member);

        memberRepository.save(member);

        member = memberRepository.getWithRoles(R.getEmail());

        log.info(member);

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
                        .member(member)
                        .build();

        log.info(memberS);

        memberSR.save(memberS);

        return memberS.getName();
    }

    @Override
    public MemberStatusDTO readOne(Long mno) {
        log.info("readOne start mno : " + mno);
        Member member = memberRepository.searchMemberByMno(mno);
        log.info(member);
        MemberStatus memberStatus = memberSR.findByMemberMno(member.getMno());


        log.info(memberStatus);

        return MemberStatusDTO.builder()
                .email(member.getEmail())
                .password(member.getPw())
                .marital_status(memberStatus.getMarital_status())
                .name(memberStatus.getName())
                .tel(memberStatus.getTel())
                .sex(memberStatus.getSex())
                .antecedents(memberStatus.getAntecedents())
                .qualifications(memberStatus.getQualifications())
                .birth(memberStatus.getBirth())
                .children_count(memberStatus.getChildren_count())
                .education(memberStatus.getEducation())
                .mno(memberStatus.getMno())
                .build();
    }

    @Override
    public String modifyOne(MemberStatusDTO R) {

        log.info(R);

        Member member = memberRepository.searchMemberByMno(R.getMno());
        member = Member.builder()
                .mno(R.getMno())
                .email(R.getEmail())
                .pw(passwordEncoder.encode(R.getPassword()))
                .build();

        log.info("memberRepository member의 수정 후 : " + member);

        MemberStatus memberSB = memberSR.findByMemberMno(member.getMno());
        log.info("member 수정 후 memberSB 구하기" + memberSB);



        MemberStatus memberS = MemberStatus.builder()
                        .mno(memberSB.getMno())
                        .name(R.getName())
                        .birth(R.getBirth())
                        .tel(R.getTel())
                        .sex(R.getSex())
                        .marital_status(R.getMarital_status())
                        .children_count(R.getChildren_count())
                        .qualifications(R.getQualifications())
                        .education(R.getEducation())
                        .antecedents(R.getAntecedents())
                        .member(member)
                        .build();

        log.info("수정 후 : " + memberS);

        memberSR.save(memberS);
        return memberS.getName();
    }

    @Override
    public List<MemberStatusDTO> list() {
        List<MemberStatusDTO> memberList = new ArrayList<>();
        int count = (int)memberSR.count();
        MemberStatusDTO memberStatusDTO;
        MemberStatus memberStatus;
        List<MemberStatus> list;
        list = memberSR.findAll();
        for(int i = 0; i < count-1; i++ ){
            memberStatus = list.get(i);
            memberStatusDTO = MemberStatusDTO.builder()
                    .email(memberStatus.getMember().getEmail())
                    .password(memberStatus.getMember().getPw())
                    .marital_status(memberStatus.getMarital_status())
                    .name(memberStatus.getName())
                    .tel(memberStatus.getTel())
                    .sex(memberStatus.getSex())
                    .antecedents(memberStatus.getAntecedents())
                    .qualifications(memberStatus.getQualifications())
                    .birth(memberStatus.getBirth())
                    .children_count(memberStatus.getChildren_count())
                    .education(memberStatus.getEducation())
                    .mno(memberStatus.getMno())
                    .build();
            memberList.add(memberStatusDTO);
        }
        log.info(memberList);


        return null;
    }


}
