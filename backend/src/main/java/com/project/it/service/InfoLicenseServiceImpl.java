package com.project.it.service;

import com.project.it.domain.InfoLicense;
import com.project.it.dto.InfoLicenseDTO;
import com.project.it.dto.PageRequestDTO;
import com.project.it.dto.PageResponseDTO;
import com.project.it.repository.InfoLicenseRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class InfoLicenseServiceImpl implements InfoLicenseService{
    private final InfoLicenseRepository infoLicenseRepository;
    private final ModelMapper modelMapper;

    //common method
    //dtoToEntity
    private InfoLicense dtoToEntity(InfoLicenseDTO dto){
        InfoLicense entity = InfoLicense.builder()
                .lno(dto.getLno())
                .rightName(dto.getRightName())
                .version(dto.getVersion())
                .purpose(dto.getPurpose())
                .copyrightHolder(dto.getCopyrightHolder())
                .price(dto.getPrice())
                .priceUnit(dto.getPriceUnit())
                .maxUserCount(dto.getMaxUserCount())
                .contact(dto.getContact())
                .build();
        return entity;
    }

    //entity to dto
    private InfoLicenseDTO entityToDto(InfoLicense entity){
        InfoLicenseDTO dto = InfoLicenseDTO.builder()
                .lno(entity.getLno())
                .rightName(entity.getRightName())
                .version(entity.getVersion())
                .purpose(entity.getPurpose())
                .copyrightHolder(entity.getCopyrightHolder())
                .price(entity.getPrice())
                .priceUnit(entity.getPriceUnit())
                .maxUserCount(entity.getMaxUserCount())
                .contact(entity.getContact())
                .build();
        return dto;
    }


    @Override  //C : 등록
    public Long register(InfoLicenseDTO infoLicenseDTO) {
        InfoLicense result = dtoToEntity(infoLicenseDTO);
        InfoLicense saveInfo = infoLicenseRepository.save(result);
        return saveInfo.getLno();
    }

    @Override //R_one : 라이선스 정보 하나만 가져오기
    public InfoLicenseDTO getOne(Long lno) {
        InfoLicense findResult = infoLicenseRepository.findById(lno).orElseThrow(EntityExistsException::new);
        InfoLicenseDTO findDto = entityToDto(findResult);
        return findDto;
    }

    @Override //R_all : 라이선스 리스트(정보 객체만 담은 리스트-> asset file 과 합쳐져야 함)
    public List<InfoLicenseDTO> getList() {
        List<InfoLicense> entityList = infoLicenseRepository.findAll(Sort.by("purpose").ascending());
        //entity->dto
        List<InfoLicenseDTO> dtoList = new ArrayList<>();
        entityList.forEach(infoLicense -> {
            InfoLicenseDTO dto =  modelMapper.map(infoLicense, InfoLicenseDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    @Override //리스트(+paging)
    public PageResponseDTO<InfoLicenseDTO> getListWithPage(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("lno").descending());
        Page<InfoLicense> result = infoLicenseRepository.searchAllByPaging(pageable);
        List<InfoLicenseDTO> dtoList = result.get().map(info->{
            InfoLicenseDTO dto = modelMapper.map(info, InfoLicenseDTO.class);
            return dto;
        }).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return PageResponseDTO.<InfoLicenseDTO>withAll().dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();
    }

    @Override //U : 라이선스 정보 변경
    public void update(InfoLicenseDTO infoLicenseDTO) {
        InfoLicense findResult = infoLicenseRepository.findById(infoLicenseDTO.getLno()).orElseThrow(EntityExistsException::new);
        findResult.changePrice(infoLicenseDTO.getPrice());
        findResult.changeUnit(infoLicenseDTO.getPriceUnit());
        findResult.changeUserCount(infoLicenseDTO.getMaxUserCount());
        findResult.changeContact(infoLicenseDTO.getContact());
        infoLicenseRepository.save(findResult);
    }

    @Override  //D: 라이선스 삭제_삭제처리(리스트에서 보이지 않게)
    public void remove(Long lno) {
        infoLicenseRepository.updateDelState(lno, true);
    }

    @Override  //D_forever : 라이선스 영구삭제_db에서 삭제
    public void removeForever(Long lno) {
    infoLicenseRepository.deleteById(lno);
    }
}
