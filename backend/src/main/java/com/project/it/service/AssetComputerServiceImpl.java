package com.project.it.service;

import com.project.it.domain.AssetComputer;
import com.project.it.domain.FileUpload;
import com.project.it.domain.InfoComputer;
import com.project.it.dto.*;
import com.project.it.repository.AssetComputerRepository;
import com.project.it.repository.FileRepository;
import com.project.it.repository.InfoComputerRepository;
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
public class AssetComputerServiceImpl implements AssetComputerService{
    private final AssetComputerRepository repoComAsset;
    private final FileRepository fileRepository;
    private final CustomFileUtil fileUtil;
    private final InfoComputerRepository repoComInfo;
    private final ModelMapper modelMapper;

    private final String category = "computer";

    @Override //C : 등록(File 등록 같이)
    public Long register(AssetComputerDTO assetComputerDTO) {
        //entity 준비
        AssetComputer entity = AssetComputer.builder()
                .purpose(assetComputerDTO.getPurpose())
                .contractCount(assetComputerDTO.getContractCount())
                .totalPrice(assetComputerDTO.getTotalPrice())
                .contractDate(assetComputerDTO.getContractDate())
                .build();
        //info entity 찾기
        Optional<InfoComputer> result = repoComInfo.findById(assetComputerDTO.getCino());
        InfoComputer infoComputer = result.orElseThrow(EntityExistsException::new);
        AssetComputer savedAssetComputer = repoComAsset.save(entity); //선저장
        //파일처리
        //public List<FileUploadDTO> saveFiles(List<MultipartFile> files, String category, Long assetNum)--파일저장
        List<MultipartFile> files = assetComputerDTO.getFiles();
        List<FileUploadDTO> saveFiles = fileUtil.saveFiles(files, category, savedAssetComputer.getCno());
        //파일DB저장
        if(saveFiles != null || saveFiles.size()>0){
            saveFiles.forEach(fileUploadDTO -> {
                FileUpload fileEntity = modelMapper.map(fileUploadDTO, FileUpload.class);
                fileEntity.setAssetNum(savedAssetComputer.getCno());
                fileEntity.setCategory(category);
                fileRepository.save(fileEntity);
            });
        }
        return savedAssetComputer.getCno();
    }

    @Override
    public AssetComputerOneDTO getOne(Long cno) {
        //file찾기
        List<FileUploadDTO> files = fileRepository.findAssetFileList(category,cno);
        Optional<AssetComputer> result = repoComAsset.findById(cno);
        AssetComputer assetComputer= result.orElseThrow(EntityExistsException::new);
        AssetComputerOneDTO oneDTO = AssetComputerOneDTO.builder()
                .cno(assetComputer.getCno())
                .cino(assetComputer.getInfoComputer().getCino())
                .productName(assetComputer.getInfoComputer().getProductName())
                .price(assetComputer.getInfoComputer().getPrice())
                .purpose(assetComputer.getPurpose())
                .contractCount(assetComputer.getContractCount())
                .totalPrice(assetComputer.getTotalPrice())
                .contractDate(assetComputer.getContractDate())
                .savedFiles(files)
                .fileCount(files.size())
                .build();
        return oneDTO;
    }

    @Override
    public PageResponseDTO<AssetComputerOneDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("cno").descending() );
        Page<AssetComputer> result = repoComAsset.searchAllWithPaging(pageable);
        List<AssetComputerOneDTO> dtoList = result.get().map(assetComputer->{
            List<FileUploadDTO> files = fileRepository.findAssetFileList(category,assetComputer.getCno());
            AssetComputerOneDTO dto = AssetComputerOneDTO.builder()
                    .cno(assetComputer.getCno())
                    .cino(assetComputer.getInfoComputer().getCino())
                    .productName(assetComputer.getInfoComputer().getProductName())
                    .price(assetComputer.getInfoComputer().getPrice())
                    .purpose(assetComputer.getPurpose())
                    .contractCount(assetComputer.getContractCount())
                    .totalPrice(assetComputer.getTotalPrice())
                    .contractDate(assetComputer.getContractDate())
                    .fileCount(files.size())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        long totalCount = result.getTotalElements();
        return PageResponseDTO.<AssetComputerOneDTO>withAll().dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();
    }

    @Override
    public void modify(AssetComputerDTO assetComputerDTO) {
        Optional<AssetComputer> result = repoComAsset.findById(assetComputerDTO.getCno());
        AssetComputer assetComputer= result.orElseThrow(EntityExistsException::new);
        assetComputer.changePurpose(assetComputerDTO.getPurpose());
        assetComputer.changeContractDate(assetComputerDTO.getContractDate());
        assetComputer.changeCountAndTotal(assetComputerDTO.getContractCount());
        repoComAsset.save(assetComputer);
        //첨부파일 변경
        List<MultipartFile> files = assetComputerDTO.getFiles();
        if(files==null||files.size()==0){//변경사항이 없다면
            return;
        }
        List<FileUploadDTO> newFiles = fileUtil.updateFiles(files, category, assetComputerDTO.getCno()); //util -> 새로 등록할 파일 리스트만 리턴(기존 파일 삭제, 신규파일 저장 완료)
        //신규파일 db 저장
        newFiles.forEach(newFile ->{
            FileUpload entity = modelMapper.map(newFile, FileUpload.class);
            fileRepository.save(entity);
        });
    }

    @Override
    public void remove(Long cno) {
    repoComAsset.deleteUpdate(cno);
    //파일찾기-> 삭제처리
    List<FileUploadDTO> fileUploadDTOS = fileRepository.findAssetFileList(category, cno);
    if(fileUploadDTOS!=null|| fileUploadDTOS.size()>0){
            fileRepository.updateDelState(category, cno, true);
        };
    }


    @Override
    public void removeForever(Long cno) {
repoComAsset.deleteById(cno);
//파일찾기->삭제
        List<FileUploadDTO> fileUploadDTOS = fileRepository.findAssetFileList(category, cno);
        if(fileUploadDTOS!=null|| fileUploadDTOS.size()>0){
            fileRepository.deleteFilesByAssetNum(category, cno);
        };

    }
}
