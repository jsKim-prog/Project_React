package com.project.it.dto;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatusDTO {

    private String email; // Member 객체 정보
    private Long mno;
    private String password; // Member 객체 정보
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
