package com.project.it.service;

import com.project.it.dto.MemberModifyDTO;
import org.springframework.transaction.annotation.Transactional;
@Transactional //여러 테이블 동시 처리용
public interface MemberService {


    void modifyMember(MemberModifyDTO memberModifyDTO);

}
