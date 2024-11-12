package com.project.it.service;

import com.project.it.domain.AssetLicense;
import com.project.it.domain.FileUpload;
import com.project.it.domain.InfoLicense;
import com.project.it.dto.*;
import com.project.it.repository.AssetLicenseRepository;
import com.project.it.repository.FileRepository;
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
    private final FileRepository fileRepository;
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
        assetLicenseDTO.setFileCount(files.size()); //**파일개수 세팅
        List<FileUploadDTO> fileUploadDTOS = fileUtil.saveFiles(files, category, ano);//폴더생성 및 실제파일 저장
        //entity 변환 및 file db 저장
        fileUploadDTOS.forEach(dto->{
            FileUpload saveFile = modelMapper.map(dto, FileUpload.class);
            saveFile.setCategory(category);
            saveFile.setAssetNum(ano);
            fileRepository.save(saveFile);
        });
        
        return ano;
    }

    @Override //R_one : 라이선스 정보 하나만 가져오기 + file 리스트+info
    public AssetLicenseOneDTO getOne(Long ano) {
        //asset정보
        Optional<AssetLicense> entity = assetLicenseRepository.findById(ano);
        AssetLicense asset = entity.orElseThrow(EntityExistsException::new);
        //파일찾기
        List<FileUploadDTO> files = fileRepository.findAssetFileList(category, asset.getAno());
        //getOne용 별도 DTO
        AssetLicenseOneDTO dto = AssetLicenseOneDTO.builder()
                .ano(asset.getAno())
                .rightName(asset.getLicense().getRightName())
                .purpose(asset.getLicense().getPurpose())
                .type(asset.getType())
                .contractStatus(asset.getContractStatus())
                .contractDate(asset.getContractDate())
                .expireDate(asset.getExpireDate())
                .contractCount(asset.getContractCount())
                .comment(asset.getComment())
                .expireYN(asset.isExpireYN())
                .totalPrice(asset.getTotalPrice())
                .priceUnit(asset.getLicense().getPriceUnit())
                .maxUserCount(asset.getLicense().getMaxUserCount())
                .totalUseCount(asset.getTotalUseCount())
                .currentUseCount(asset.getCurrentUseCount())
                .licenseId(asset.getLicense().getLno())
                .savedFiles(files)
                .fileCount(files.size())
                .contact(asset.getLicense().getContact())
                .build();
        return dto;
    }

    @Override  //R_all : 라이선스(asset) 리스트+ file 개수
    public PageResponseDTO<AssetLicenseListDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("ano").descending());
        //select asset, count(files) -> Object[Asset(EN), FilesCount(int)]
        Page<AssetLicense> result = assetLicenseRepository.getList(pageable);
        List<AssetLicenseListDTO> dtoList = result.get().map(asset->{
            AssetLicenseListDTO dto = AssetLicenseListDTO.builder()
                    .ano(asset.getAno())
                    .type(asset.getType())
                    .rightName(asset.getLicense().getRightName())
                    .purpose(asset.getLicense().getPurpose())
                    .currentUseCount(asset.getCurrentUseCount())
                    .totalUseCount(asset.getTotalUseCount())
                    .expireDate(asset.getExpireDate())
                    .fileCount(asset.getFileCount())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return PageResponseDTO.<AssetLicenseListDTO>withAll().dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();
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
        assetLicenseDTO.setFileCount(files.size()); //**변경파일개수 등록
        if (files==null||files.size()==0){//변경사항이 없다면
        return;
        }
        List<FileUploadDTO> newFiles = fileUtil.updateFiles(files, category, assetLicenseDTO.getAno()); //util -> 새로 등록할 파일 리스트만 리턴(기존 파일 삭제, 신규파일 저장 완료)
        //신규파일 db 저장
        newFiles.forEach(newFile ->{
            FileUpload entity = modelMapper.map(newFile, FileUpload.class);
            fileRepository.save(entity);
        });
    }

    @Override  //D: 라이선스 삭제_삭제처리(리스트에서 보이지 않게: file list도 삭제처리 )
    public void remove(Long ano) {
        assetLicenseRepository.updateToDelete(ano, true);
        List<FileUploadDTO> fileUploadDTOS = fileRepository.findAssetFileList(category, ano);
        fileUploadDTOS.forEach(removeFile->{
            fileRepository.updateDelState(category, ano, true);
        });
    }

    @Override  //D_forever : 라이선스 영구삭제_db에서 삭제(관련 파일까지 삭제)
    public void removeForever(Long ano) {
        assetLicenseRepository.deleteById(ano);
        List<FileUploadDTO> files = fileRepository.findAssetFileList(category, ano);
        fileUtil.deleteFiles(files); //file data 삭제
        files.forEach(removeFile ->{
            fileRepository.deleteById(removeFile.getFno());
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
                .currentUseCount(entity.getCurrentUseCount())
                .build();
        return dto;
    }

    private AssetLicense dtoToEntity(AssetLicenseDTO dto){
        Optional<InfoLicense> result = infoLicenseRepository.findById(dto.getLicenseId());
        InfoLicense license = result.orElseThrow(EntityExistsException::new);
        AssetLicense entity = AssetLicense.builder()
                .type(dto.getType())
                .contractStatus(dto.getContractStatus())
                .contractDate(dto.getContractDate())
                .expireDate(dto.getExpireDate())
                .contractCount(dto.getContractCount())
                .comment(dto.getComment())
                .license(license)
                .fileCount(dto.getFileCount())
                .build();
        return entity;
    }
   
}
