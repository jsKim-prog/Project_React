package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_assetComputer")
@Getter
@ToString(exclude = {"infoComputer", "infoMonitor"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManageComputer extends ManageCategory { //물적자원_category : computer/Desktop, Monitor, Notebook, printer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate; //구입일
    private int totalCount; //전체(구입)개수
    private int useCount; //현재 사용중 개수
    private String inCharge;//책임자(등록자 email)
    @Embedded
    private InfoMaker infoMaker; //제조사, 시리얼 번호, 품번

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infoNum_com")
    private InfoComputer infoComputer; //Desktop,Notebook 사양정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infoNum_monitor")
    private InfoMonitor infoMonitor; //Monitor 사양정보


    //변경용 메서드
    public void changeTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public void changeUseCount(int useCount){
        this.useCount = useCount;
    }

    public void changeInCharge(String inCharge){
        this.inCharge = inCharge;
    }

}
