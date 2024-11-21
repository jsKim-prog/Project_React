package com.project.it.service;

import com.project.it.domain.Application;
import com.project.it.domain.ApplicationFile;
import com.project.it.domain.JoinStatus;
import com.project.it.domain.OrganizationTeam;
import com.project.it.dto.ApplicationDTO;
import com.project.it.dto.PageRequestDTO;
import com.project.it.dto.PageResponseDTO;
import com.project.it.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository APR;

    @Override
    public Long register(ApplicationDTO applicationDTO) { // C
        log.info("지원서 등록 : " + applicationDTO.toString());
        Application application = dtoToEntity(applicationDTO);
        application.changeJoinStatus(JoinStatus.WAITING);
        Application result = APR.save(application);

        log.info(result);

        return result.getNo();
    }

    @Override
    public Long modify(ApplicationDTO applicationDTO) { //U
        Application application = dtoToEntity(applicationDTO);
        Application result = APR.save(application);

        log.info(result);

        return result.getNo();
    }

    @Override
    public ApplicationDTO getOne(String no) { //R-O
        Optional<Application> application = APR.findById(no);
        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .no(application.get().getNo())
                .name(application.get().getName())
                .phoneNum(application.get().getPhoneNum())
                .mail(application.get().getMail())
                .start_date(application.get().getStart_date())
                .build();

        List<ApplicationFile> applicationFilesLists = application.get().getApplicationFileList();
        applicationDTO.getUploadFileNames().add(applicationFilesLists.get(0).getFileName());

        applicationDTO.getOrganizationTeam().add(application.get().getOrganizationTeamList().get(0).toString());
        applicationDTO.getJoinStatus().add(application.get().getJoinStatusList().get(0).toString());
        log.info(applicationDTO);
        return applicationDTO;
    }


    @Override
    public PageResponseDTO<ApplicationDTO> getList(PageRequestDTO pageRequestDTO) { //R-List
        log.info("getList..........");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,  // 페이지 시작 번호가 0부터 시작하므로
                pageRequestDTO.getSize(),
                Sort.by("no").descending());

        Page<Application> result = null;

        String searchQuery = pageRequestDTO.getSearchQuery(); // 검색어
        if (searchQuery == null || searchQuery.isEmpty()) {
            // 검색어 없이 전체 리스트
            result = APR.selectList(pageable);
        } else {
            try {
                // 지원결과로 검색
                JoinStatus joinStatus = JoinStatus.fromString(searchQuery);
                result = APR.findAllByJoinStatus(joinStatus, pageable);
            } catch (IllegalArgumentException e) {
                try {
                    // 지원부서로 검색
                    OrganizationTeam organizationTeam = OrganizationTeam.fromKoreanName(searchQuery);
                    result = APR.findAllByOrganizationTeam(organizationTeam, pageable);
                } catch (IllegalArgumentException ex) {
                    // 이름으로 검색
                    result = APR.findAllByName(searchQuery, pageable);
                }
            }
        }

        List<ApplicationDTO> dtoList = new ArrayList<>();
        log.info("Page result : " + result.stream().toList().get(0));
        Application application;
        ApplicationDTO applicationDTO;

        for (int i = 0; i <= result.stream().toList().size() - 1; i++) {

            application = result.stream().toList().get(i);
            applicationDTO = ApplicationDTO.builder()
                    .no(application.getNo())
                    .name(application.getName())
                    .phoneNum(application.getPhoneNum())
                    .mail(application.getMail())
                    .start_date(application.getStart_date())
                    .build();
            if (application.getJoinStatusList() != null && !application.getJoinStatusList().isEmpty()) {
                applicationDTO.getJoinStatus().add(application.getJoinStatusList().get(0).toString());
            }

            if (application.getOrganizationTeamList() != null && !application.getOrganizationTeamList().isEmpty()) {
                applicationDTO.getOrganizationTeam().add(application.getOrganizationTeamList().get(0).toString());
            }

            if (application.getApplicationFileList() != null && !application.getApplicationFileList().isEmpty()) {
                applicationDTO.getUploadFileNames().add(application.getApplicationFileList().get(0).getFileName());
            }

            dtoList.add(applicationDTO);

        }

        Long totalCount = result.getTotalElements();

        log.info(dtoList);

        return PageResponseDTO.<ApplicationDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }


    private Application dtoToEntity(ApplicationDTO applicationDTO){  // dto를 Entity로 변환

        Application application = Application.builder()
                .no(applicationDTO.getNo())
                .name(applicationDTO.getName())
                .phoneNum(applicationDTO.getPhoneNum())
                .mail(applicationDTO.getMail())
                .start_date(applicationDTO.getStart_date())
                .build();

        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = applicationDTO.getUploadFileNames();

        if(uploadFileNames != null){
            uploadFileNames.stream().forEach(fileName -> {
                application.addAppString(fileName);
            });}

        // 지원 부서
        List<String> organizationTeamName = applicationDTO.getOrganizationTeam();

        if(organizationTeamName != null){
            organizationTeamName.stream().forEach(teamName -> {
                application.addOTL(OrganizationTeam.fromKoreanName(teamName));
            });
        }

        // 입사 상태
        List<String> joinStatus = applicationDTO.getJoinStatus();
        if(joinStatus != null){
            joinStatus.stream().forEach(status -> {
                application.changeJoinStatus(JoinStatus.fromString(status));
            });
        }

        return application;
    } //dtoToEntity 메서드 종료

    private ApplicationDTO entityToDto(Application application) {  // Entity를 dto로 변환

        List<String> joinStatus = new ArrayList<>();
        joinStatus.add(application.getJoinStatusList().get(0).toString());

        List<String> organizationTeam = new ArrayList<>();
        organizationTeam.add(application.getOrganizationTeamList().get(0).toString());

        List<String> uploadFileNames = new ArrayList<>();
        uploadFileNames.add(application.getApplicationFileList().get(0).toString());

        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .no(application.getNo())
                .name(application.getName())
                .phoneNum(application.getPhoneNum())
                .mail(application.getMail())
                .start_date(application.getStart_date())
                .uploadFileNames(uploadFileNames)
                .organizationTeam(organizationTeam)
                .joinStatus(joinStatus)
                .build();

        return applicationDTO;
    }

}
