package com.project.it.service;

import com.project.it.dto.InfoLicenseDTO;
import com.project.it.dto.InfoPartnersDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class LicenseTests {
    @Autowired
    private InfoPartnersService partnersService;
    @Autowired
    private InfoLicenseService serviceInfo;
    @Autowired
    private AssetLicenseService serviceAsset;

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
                .totalPrice(826680)
                .priceUnit("person")
                .maxUserCount(1)
                .contact("https://www.jetbrains.com/ko-kr/idea/buy/?section=commercial&billing=yearly")
                .build();

        InfoLicenseDTO dto2 = InfoLicenseDTO.builder()
                .rightName("Adobe Creative Cloud")
                .version("Creative Cloud")
                .purpose("design")
                .copyrightHolder("Adobe")
                .totalPrice(104000)
                .priceUnit("month")
                .maxUserCount(1)
                .contact("https://www.adobe.com/kr/creativecloud/plans.html")
                .build();
        Long ano1 = serviceInfo.register(dto1);
        Long ano2 = serviceInfo.register(dto2);
        log.info("등록된 ano :"+ano1 + "/"+ano2);
    }

    //info 수정
    @Test
    public void modInfoLicense(){
        InfoLicenseDTO modinfo = serviceInfo.getOne(1L);
        modinfo.setPriceUnit("year");
        serviceInfo.update(modinfo);
        log.info(modinfo);
    }
}
