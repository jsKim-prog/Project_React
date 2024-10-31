package com.project.it.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class AssetTests {
  //  @Autowired
 //   private AssetComputerRepository assetComputerRepository;
 //   @Autowired
 //   private AssetSoftwareRepository assetSoftwareRepository;
 //   @Autowired
  //  private InfoComputerRepository infoComputerRepository;

    //등록 테스트
 /*   @Test
    public void insertComputerTest(){
        InfoMaker maker = InfoMaker.builder()
                .maker("HP")
                .serialNum("F8F53BDB-2CD9-44FE-88FE-932F73F27DAF")
                .productNum("00330-80000-00000-AA471")
                .build();
        Optional<InfoComputer> result = infoComputerRepository.findById(1L);
        InfoComputer infoComputer = result.orElseThrow();
        ManageComputer assetComputer = ManageComputer.builder()
                .purchaseDate(LocalDate.now())
                .totalCount(10)
                .useCount(0)
                .infoMaker(maker)
                .inCharge("tester@test.com")
                .infoComputer(infoComputer)
                .build();
        assetComputer.setLevel_1("Computer");
        assetComputer.setLevel_2("Desktop");
        assetComputerRepository.save(assetComputer);
        log.info("------등록된 com id : "+assetComputer.getId());
    } */

}
