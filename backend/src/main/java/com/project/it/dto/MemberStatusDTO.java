package com.project.it.dto;


import com.project.it.domain.MemberRole;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatusDTO {

    private String email; // Member 객체 정보
    private Long mno; // Member 객체 정보
    private String password; // Member 객체 정보
    private LocalDateTime start_date; // Member 객체 정보
    private String memberRole; // Member 객체 정보
    private String name;
    private String birth;
    private String tel;
    private String sex;
    private String marital_status;
    private Integer children_count;
    private String qualifications;
    private String education;
    private String antecedents;




}
