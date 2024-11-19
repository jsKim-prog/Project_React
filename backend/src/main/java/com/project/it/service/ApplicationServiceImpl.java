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
        Application application = dtoToEntity(applicationDTO);
        application.changeJoinStatus(JoinStatus.WAITING);
        Application result = APR.save(application);

        log.info(result);

        return result.getNo();
    }

    @Override
    public ApplicationDTO getOne(String no) { //R-O
        Optional<Application> application = APR.findById(no);
        ApplicationDTO applicationDTD = entityToDto(application.get());
        log.info(applicationDTD);
        return applicationDTD;
    }


    @Override
    public PageResponseDTO<ApplicationDTO> getList(PageRequestDTO pageRequestDTO) { //R-List
        log.info("getList..........");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,  //페이지 시작 번호가 0부터 시작하므로
                pageRequestDTO.getSize(),
                Sort.by("no").descending());

        Page<Application> result = APR.selectList(pageable);
        List<ApplicationDTO> dtoList = new ArrayList<>();

        log.info("Page result : " + result.stream().toList().get(0));
        Application application;
        ApplicationDTO applicationDTO;

        for(int i = 0; i<=result.stream().toList().size()-1; i++){

                application = result.stream().toList().get(i);
                applicationDTO = ApplicationDTO.builder()
                        .no(application.getNo())
                        .name(application.getName())
                        .phoneNum(application.getPhoneNum())
                        .mail(application.getMail())
                        .build();
                if (application.getJoinStatusList() != null) {
                    applicationDTO.getJoinStatus().add(application.getJoinStatusList().get(0).toString());
                }
                if (application.getOrganizationTeamList() != null) {
                    applicationDTO.getOrganizationTeam().add(application.getOrganizationTeamList().get(0).toString());
                }
                if (application.getApplicationFileList() != null) {
                    applicationDTO.getUploadFileNames().add(application.getApplicationFileList().get(0).toString());
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
                application.addOTL(OrganizationTeam.fromString(teamName));
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
                .uploadFileNames(uploadFileNames)
                .organizationTeam(organizationTeam)
                .joinStatus(joinStatus)
                .build();

        return applicationDTO;
    }

}
