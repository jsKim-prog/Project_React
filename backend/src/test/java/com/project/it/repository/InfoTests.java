package com.project.it.repository;

import com.project.it.domain.InfoComputer;
import com.project.it.domain.InfoMonitor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class InfoTests {
    @Autowired
    private InfoComputerRepository infoComputerRepository;
    @Autowired
    private InfoMonitorRepository infoMonitorRepository;

    //더미 입력
    @Test
    public void insertCominfoTest(){
        InfoComputer infoComputer = InfoComputer.builder()
                .cpu("Intel(R) Core(TM) i5-6600 CPU @ 3.30GHz")
                .gpu("NVIDIA Geforce GTX 1060 3GB")
                .ram("16.0GB")
               .capacity("680GB")
                .build();

        infoComputerRepository.save(infoComputer);
        log.info("===등록된 id : "+infoComputer.getId());

    }

    @Test
    public void insertMonitorInfoTest(){
        InfoMonitor infoMonitor = InfoMonitor.builder()
                .size(27)
                .resolution("QHD")
                .build();
        infoMonitorRepository.save(infoMonitor);
    }
}
