package com.project.it.service;

import com.project.it.constant.ContractStatus;
import com.project.it.constant.PriceUnit;
import com.project.it.constant.RightType;
import com.project.it.dto.*;
import com.project.it.util.CustomFileUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
@Log4j2
public class LicenseTests {
    @Autowired
    private InfoPartnersService partnersService;
    @Autowired
    private InfoLicenseService serviceInfo;
    @Autowired
    private AssetLicenseService serviceAsset;
    @Autowired
    private CustomFileUtil fileUtil;

    //고객사 등록(Dummy)
    @Test
    public void insertPartnerTest(){
        DecimalFormat front = new DecimalFormat("000");
        DecimalFormat middle = new DecimalFormat("00");
        DecimalFormat last = new DecimalFormat("00000");
        DecimalFormat four = new DecimalFormat("0000");
        IntStream.rangeClosed(1, 20).forEach(i->{
            InfoPartnersDTO infoPartnersDTO = InfoPartnersDTO.builder()
                    .comName("회사명"+i).coNum(front.format(i)+"-"+middle.format(i)+"-"+last.format(i))
                    .phone(front.format(i)+"-"+four.format(i)+"-"+four.format(i)).site("www.homepage"+i+".com").address("주소"+i)
                    .bizType("업종"+(i/10))
                    .build();
            Long cno = partnersService.register(infoPartnersDTO);
            log.info("등록된 회사 : "+cno);
        });

    }

    //라이선스 -info등록
    @Test
    public void insertInfoLicenseTest(){
        InfoLicenseDTO dto1 = InfoLicenseDTO.builder()
                .rightName("IntelliJ IDEA Ultimate")
                .version("IntelliJ IDEA")
                .purpose("programming")
                .copyrightHolder("JetBrains s.r.o.")
                .price(826680)
                .priceUnit(PriceUnit.PERSON)
                .maxUserCount(1)
                .contact("https://www.jetbrains.com/ko-kr/idea/buy/?section=commercial&billing=yearly")
                .build();

        InfoLicenseDTO dto2 = InfoLicenseDTO.builder()
                .rightName("Adobe Creative Cloud")
                .version("Creative Cloud")
                .purpose("design")
                .copyrightHolder("Adobe")
                .price(104000)
                .priceUnit(PriceUnit.MONTHLY)
                .maxUserCount(1)
                .contact("https://www.adobe.com/kr/creativecloud/plans.html")
                .build();
        Long ano1 = serviceInfo.register(dto1);
        Long ano2 = serviceInfo.register(dto2);
        log.info("등록된 ano :"+ano1 + "/"+ano2);
    }


    //info dummy
    @Test
    public void infoDummyInsertTest(){
        DecimalFormat fourNum = new DecimalFormat("0000");
        List<InfoLicenseDTO> list = new ArrayList<>();
        LongStream.rangeClosed(1, 10).forEach(i->{
            InfoLicenseDTO dto = InfoLicenseDTO.builder()
                    .rightName("라이선스"+fourNum.format(i))
                    .version("version"+i)
                    .purpose("dummy")
                    .copyrightHolder("dummy")
                    .price((int) i*10000)
                    .priceUnit(PriceUnit.MONTHLY)
                    .maxUserCount(1)
                    .contact("DummyData")
                    .build();
            serviceInfo.register(dto);
            list.add(dto);
        });
        list.forEach(infoLicenseDTO -> log.info(infoLicenseDTO));
    }

    //info 수정
    @Test
    public void modInfoLicense(){
        InfoLicenseDTO modinfo = serviceInfo.getOne(2L);
        modinfo.setPriceUnit(PriceUnit.MONTHLY);
        serviceInfo.update(modinfo);
        log.info(modinfo);
    }

    //Asset 등록
    @Test
    public void insertAssetTest(){
      //  InfoLicenseDTO infoDto = serviceInfo.getOne(1L);
        LocalDate today = LocalDate.now();
        AssetLicenseDTO dto = AssetLicenseDTO.builder()
                .type(RightType.LICENSE)
                .contractStatus(ContractStatus.NEW)
                .contractCount(5)
                .contractDate(today)
                .expireDate(today.plusYears(1L))
                .licenseId(1L)
                .build();
        serviceAsset.register(dto);
    }


    //Asset dummy
    @Test
    public void assetDummyInsertTest(){
        //DecimalFormat fourNum = new DecimalFormat("0000");
        LocalDate today = LocalDate.now();
        List<AssetLicenseDTO> list = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(i->{
            Long licenseID  = (long)i ;
            log.info("randomID : "+licenseID);
            AssetLicenseDTO dto = AssetLicenseDTO.builder()
                    .type(RightType.LICENSE)
                    .contractStatus(ContractStatus.NEW)
                    .contractCount(5+i)
                    .contractDate(today)
                    .expireDate(today.plusMonths(1L))
                    .licenseId(licenseID)
                    .build();
            serviceAsset.register(dto);
            list.add(dto);
        });
        list.forEach(infoLicenseDTO -> log.info(infoLicenseDTO));
    }


    //리스트
    @Test
    public void assetListTest(){
        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setSize(5);
        serviceAsset.getList(requestDTO);
        PageResponseDTO<AssetLicenseListDTO> assetList = serviceAsset.getList(requestDTO);
        List<AssetLicenseListDTO> list = assetList.getDtoList();
        log.info("리스트 개수 : "+list.size());
        list.forEach(asset->{
            log.info(asset);
        });
    }


}
