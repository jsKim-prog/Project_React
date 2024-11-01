package com.project.it.service;

import com.project.it.domain.AssetLicense;
import com.project.it.domain.FileUpload;
import com.project.it.domain.InfoLicense;
import com.project.it.dto.AssetLicenseDTO;
import com.project.it.dto.FileUploadDTO;
import com.project.it.dto.PageRequestDTO;
import com.project.it.dto.PageResponseDTO;
import com.project.it.repository.AssetLicenseRepository;
import com.project.it.repository.FileUploadRepository;
import com.project.it.repository.InfoLicenseRepository;
import com.project.it.util.CustomFileUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AssetLicenseServiceImpl implements AssetLicenseService{
    private final AssetLicenseRepository assetLicenseRepository;
    private final ModelMapper modelMapper;
    private final CustomFileUtil fileUtil;
    private final FileUploadRepository fileUploadRepository;
    private final InfoLicenseRepository infoLicenseRepository;
    
    private final String category = "license";

    @Override //C : 등록(File 등록 같이)
    public Long register(AssetLicenseDTO assetLicenseDTO) {
        //ano 획득-> asset 선등록
        AssetLicense entity = dtoToEntity(assetLicenseDTO);
        assetLicenseRepository.save(entity);
        Long ano = entity.getAno();
        //파일저장 및 DTO 리스트 획득
        List<MultipartFile> files = assetLicenseDTO.getFiles();
        List<FileUploadDTO> fileUploadDTOS = fileUtil.saveFiles(files, category, ano);//폴더생성 및 실제파일 저장
        //entity 변환 및 file db 저장
        fileUploadDTOS.forEach(dto->{
            FileUpload saveFile = modelMapper.map(dto,FileUpload.class);
            saveFile.setCategory(category);
            saveFile.setAssetNum(ano);
            fileUploadRepository.save(saveFile);
        });
        
        return ano;
    }

    @Override //R_one : 라이선스 정보 하나만 가져오기 + file 리스트
    public AssetLicenseDTO getOne(Long ano) {
        return assetLicenseRepository.findByAnoWithFiles(ano);
    }

    @Override  //R_all : 라이선스(asset) 리스트+ file 개수
    public PageResponseDTO<AssetLicenseDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("ano").descending());
        //select asset, count(files) -> Object[Asset(EN), FilesCount(int)]
        Page<Object[]> result = assetLicenseRepository.getList(pageable);
        List<AssetLicenseDTO> dtoList = result.get().map(arr->{
            AssetLicense assetLicense = (AssetLicense) arr[0];
            AssetLicenseDTO dto = AssetLicenseDTO.builder()
                    .ano(assetLicense.getAno())
                    .type(assetLicense.getType())
                    .contractStatus(assetLicense.getContractStatus())
                    .contractDate(assetLicense.getContractDate())
                    .expireDate(assetLicense.getExpireDate())
                    .comment(assetLicense.getComment())
                    .expireYN(assetLicense.isExpireYN())
                    .fileCount((int) arr[1])
                    .licenseId(assetLicense.getLicense().getLno())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return PageResponseDTO.<AssetLicenseDTO>withAll().dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();
    }

    @Override  //U : 라이선스 관리 정보 변경
    public void update(AssetLicenseDTO assetLicenseDTO) {
       //asset 변경
        Optional<AssetLicense> result = assetLicenseRepository.findById(assetLicenseDTO.getAno());
        AssetLicense findEntity = result.orElseThrow(EntityExistsException::new);
        findEntity.changeComment(assetLicenseDTO.getComment());
        findEntity.changeDeleteState(true);

        assetLicenseRepository.save(findEntity);

        //첨부파일 변경
        List<MultipartFile> files = assetLicenseDTO.getFiles();
        if (files==null||files.size()==0){//변경사항이 없다면
        return;
        }
        List<FileUploadDTO> newFiles = fileUtil.updateFiles(files, category, assetLicenseDTO.getAno()); //util -> 새로 등록할 파일 리스트만 리턴(기존 파일 삭제, 신규파일 저장 완료)
        //신규파일 db 저장
        newFiles.forEach(newFile ->{
            FileUpload entity = modelMapper.map(newFile, FileUpload.class);
            fileUploadRepository.save(entity);
        });
    }

    @Override  //D: 라이선스 삭제_삭제처리(리스트에서 보이지 않게: file list도 삭제처리 )
    public void remove(Long ano) {
        assetLicenseRepository.updateToDelete(ano, true);
        List<FileUploadDTO> fileUploadDTOS = fileUploadRepository.findAssetFileList(category, ano);
        fileUploadDTOS.forEach(removeFile->{
            fileUploadRepository.updateDelState(category, ano, true);
        });
    }

    @Override  //D_forever : 라이선스 영구삭제_db에서 삭제(관련 파일까지 삭제)
    public void removeForever(Long ano) {
        assetLicenseRepository.deleteById(ano);
        List<FileUploadDTO> files = fileUploadRepository.findAssetFileList(category, ano);
        fileUtil.deleteFiles(files); //file data 삭제
        files.forEach(removeFile ->{
            fileUploadRepository.deleteById(removeFile.getFno());
        }); //db 리스트 삭제
    }

    //변환메서드
    private AssetLicenseDTO entityToDto(AssetLicense entity){
        AssetLicenseDTO dto = AssetLicenseDTO.builder()
                .ano(entity.getAno())
                .type(entity.getType())
                .contractStatus(entity.getContractStatus())
                .contractDate(entity.getContractDate())
                .expireDate(entity.getExpireDate())
                .contractCount(entity.getContractCount())
                .comment(entity.getComment())
                .expireYN(entity.isExpireYN())
                .licenseId(entity.getLicense().getLno())
                .totalUseCount(entity.getTotalUseCount())
                .build();
        return dto;
    }

    private AssetLicense dtoToEntity(AssetLicenseDTO dto){
        Optional<InfoLicense> result = infoLicenseRepository.findById(dto.getAno());
        InfoLicense license = result.orElseThrow(EntityExistsException::new);
        AssetLicense entity = AssetLicense.builder()
                .type(dto.getType())
                .contractStatus(dto.getContractStatus())
                .contractDate(dto.getContractDate())
                .expireDate(dto.getExpireDate())
                .contractCount(dto.getContractCount())
                .comment(dto.getComment())
                .license(license)
                .build();
        return entity;
    }
   
}