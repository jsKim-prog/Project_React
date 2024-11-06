package com.project.it.dto;

import com.project.it.domain.Member;
import com.project.it.domain.MemberStatus;
import lombok.Data;

@Data
public class MemberModifyDTO {

   Member member;
   MemberStatus memberStatus;
}
