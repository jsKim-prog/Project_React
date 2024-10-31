package com.project.it.dto;

import com.project.it.domain.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoLicenseDTO {
    private Long lno; //관리번호
    private String rightName; //이름(특허/계약명) : ex_Adobe Photoshop
    private String version; //제품 버전
    private String purpose; //용도 : 디자인, 개발..
    private String copyrightHolder; //저작권자(계약회사)
    private int totalPrice; //금액(계약총액)
    private String priceUnit; //계약단위(월단위, 년단위, 인원수 단위...)
    private double unitPrice; //계약단위별 금액(자동계산)
    private int maxUserCount; //최대 사용 가능 인원
    private String contact; //이용경로
    private boolean deleteOrNot; //삭제 여부


    //생성자(등록용)
    public InfoLicenseDTO(String rightName, String version, String purpose, int totalPrice, String priceUnit, int maxUserCount){
        this.rightName = rightName;
        this.version = version;
        this.purpose = purpose;
        this.totalPrice = totalPrice;
        this.priceUnit = priceUnit;
        this.maxUserCount = maxUserCount;
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
