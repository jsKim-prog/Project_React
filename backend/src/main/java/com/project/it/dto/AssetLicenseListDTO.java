package com.project.it.dto;

import com.project.it.constant.RightType;
import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AssetLicenseListDTO { //리스트 전송용 dto
    private Long ano;  //AssetLicenseDTO
    private RightType type; //AssetLicenseDTO
    private String rightName; //InfoLicenseDTO
    private String purpose; //InfoLicenseDTO
    private int currentUseCount ; //AssetLicenseDTO
    private int totalUseCount; //AssetLicenseDTO
    private LocalDate expireDate; //AssetLicenseDTO

    private int fileCount;
}
