package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_status")
public class MemberStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    //status 멤버
    @Column(nullable = false)
    private String name;
    //사원 이름
    @Column(nullable = false)
    private String birth;
    //생년월일
    @Column(nullable = false)
    private String tel;
    //전화번호
    @Column(nullable = false)
    private String sex;
    //성별
    private String marital_status;
    //기혼 유무
    private Integer children_count;
    //자녀 수
    private String qualifications;
    //자격증
    private String education;
    //학력
    private String antecedents;
    //경력
    @ManyToOne
    @JoinColumn(name = "member_mno")
    private Member member;


    //method
    public void addMember(Member member){
        this.member = member;
    }
}
