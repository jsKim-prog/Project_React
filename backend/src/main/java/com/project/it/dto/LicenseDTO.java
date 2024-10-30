package com.project.it.dto;

import com.project.it.domain.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDTO {
    private Long id; //관리번호
    private String type; //권리유형 : 자사특허, (타사)사용권
    private String rightName; //이름(특허/계약명) : ex_Adobe Photoshop
    private String version; //제품 버전
    private String purpose; //용도 : 디자인, 개발..
    private int totalPrice; //금액(계약총액)
    private String priceUnit; //계약단위(월단위, 년단위, 인원수 단위...)
    private double unitPrice; //계약단위별 금액(자동계산)
    private String contractDate; //취득일(계약일)
    private String expireDate; //만료일
    private int maxUserCount; //최대 사용 가능 인원
    private String comment; //기타 설명
    private boolean expireYN; //만료여부
    private ContractStatus contractStatus; //계약구분(신규, 재계약, 갱신..)

    private String writer, rewriter;
    private LocalDate regDate, updateDate;

    //생성자(등록용)
    public LicenseDTO(String type, String rightName, String version, String purpose, int totalPrice, String priceUnit, String contractDate, String expireDate, int maxUserCount, String comment, ContractStatus contractStatus){
        this.type = type;
        this.rightName = rightName;
        this.version = version;
        this.purpose = purpose;
        this.totalPrice = totalPrice;
        this.priceUnit = priceUnit;
        this.contractDate = contractDate;
        this.expireDate = expireDate;
        this.maxUserCount = maxUserCount;
        this.comment = comment;
        this.contractStatus =contractStatus;
        this.unitPrice = calculatorUnitPrice(priceUnit, totalPrice);
    }



    //unitPrice 자동계산
    public double calculatorUnitPrice(String priceUnit, int totalPrice){
        if(priceUnit.equals("person")){ //인당
            int maxPerson = this.maxUserCount;
            return  Math.round(totalPrice/maxPerson) ;
        } else if (priceUnit.equals("year")) { //년
            return Math.round(totalPrice/12);
        }else{ //월단위 - 기본
            return totalPrice;
        }
    }
}
