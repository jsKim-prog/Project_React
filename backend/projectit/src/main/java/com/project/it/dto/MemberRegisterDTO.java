package com.project.it.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;



@Getter
@ToString
@Builder
@AllArgsConstructor
public class MemberRegisterDTO {

    private String email;
    private String mno;
    private String pw;
    private String name;
    private String birth;
    private String tel;
    private String sex;
    private String marital_status;
    private int children_count;
    private String qualifications;
    private String education;
    private String antecedents;


}
