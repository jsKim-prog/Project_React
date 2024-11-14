package com.project.it.service;

import com.project.it.domain.*;
import com.project.it.dto.*;
import com.project.it.repository.MemberRepository;
import com.project.it.repository.MemberStatusRepository;
import com.project.it.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final OrganizationRepository orgRepo;


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
        //회원가입시 기본 팀 정보를 지정
        Organization organization =
                Organization.builder()
                        .teamName("")
                        .member(member)
                        .build();

        organization.addOrganizationTeam(OrganizationTeam.AWAIT);//대기

        orgRepo.save(organization);

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
            String memberRole = memberRepository.searchMemberByMno(memberStatus.getMember().getMno()).getMemberRoleList().toString();
            memberStatusDTO = MemberStatusDTO.builder()
                    .email(memberStatus.getMember().getEmail())
                    .mno(memberStatus.getMember().getMno())
                    .start_date(memberStatus.getMember().getStart_date())
                    .memberRole(memberRole)
                    .name(memberStatus.getName())
                    .tel(memberStatus.getTel())
                    .birth(memberStatus.getBirth())
                    .education(memberStatus.getEducation())
                    .build();
            memberList.add(memberStatusDTO);
        }
        log.info("serviceImpl 완료");


        return memberList;
    }

    @Override
    public PageResponseDTO<MemberStatusDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("getList..............");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,  //페이지 시작 번호가 0부터 시작하므로
                pageRequestDTO.getSize(),
                Sort.by("mno").descending());

        Page<MemberStatus> result = memberSR.selectList(pageable);
        List<MemberStatusDTO> list = new ArrayList<>();

        log.info("Page result : " + result.stream().toList().get(0));
        MemberStatus memberStatus;
        MemberStatusDTO memberStatusDTO;

        for(int i = 0; i<result.getSize(); i++){
            memberStatus = result.stream().toList().get(i);
            Member member = memberRepository.searchMemberByMno(memberStatus.getMno());
            String memberRole = member.getMemberRoleList().get(member.getMemberRoleList().size()-1).toString();


            memberStatusDTO = MemberStatusDTO.builder()
                    .email(memberStatus.getMember().getEmail())
                    .mno(memberStatus.getMember().getMno())
                    .start_date(memberStatus.getMember().getStart_date())
                    .memberRole(memberRole)
                    .name(memberStatus.getName())
                    .tel(memberStatus.getTel())
                    .birth(memberStatus.getBirth())
                    .education(memberStatus.getEducation())
                    .build();
            list.add(memberStatusDTO);
        }
        long totalCount = result.getTotalElements();

        return PageResponseDTO.<MemberStatusDTO>withAll()
                .dtoList(list)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }

    @Override
    public void modifyMemberRole(MemberTeamDTO mtDTO) {
        Member member = memberRepository.searchMemberByMno(mtDTO.getMno());
        member.addRole(MemberRole.fromString(mtDTO.getMemberRole()));
        memberRepository.save(member);


    }


}
