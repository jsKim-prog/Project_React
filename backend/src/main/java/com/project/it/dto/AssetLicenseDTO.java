package com.project.it.dto;

import com.project.it.constant.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetLicenseDTO {
    private Long ano;
    private String type; //권리유형 : 자사특허, (타사)사용권
    private ContractStatus contractStatus; //계약구분(신규, 재계약, 갱신..)
    private LocalDate contractDate; //취득일(계약일)
    private LocalDate expireDate; //만료일
    private int contractCount; //상품구입 개수
    private String comment; //기타 설명
    private boolean expireYN; //만료여부
    private boolean deleteOrNot; //삭제처리 여부

    private int totalUseCount; //총 사용가능 개수(maxcount*contractCount)

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); //첨부파일
    
    private int fileCount; //첨부파일 개수

    private Long licenseId; //관련 라이선스 정보

}
