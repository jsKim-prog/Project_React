package com.project.it.service;

import com.project.it.dto.InfoPartnersDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SWTests {
    @Autowired
    private InfoPartnersService partnersService;

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
}
