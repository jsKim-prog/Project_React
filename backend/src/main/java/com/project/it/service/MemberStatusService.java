package com.project.it.service;

import com.project.it.dto.MemberStatusDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberStatusService{

    String register(MemberStatusDTO R);

    MemberStatusDTO readOne(Long mno);

    String modifyOne(MemberStatusDTO mdto);

    List<MemberStatusDTO> list();


}
