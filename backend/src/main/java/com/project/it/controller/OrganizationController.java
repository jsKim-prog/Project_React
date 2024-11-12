package com.project.it.controller;

import com.project.it.dto.MemberStatusDTO;
import com.project.it.dto.PageRequestDTO;
import com.project.it.dto.PageResponseDTO;
import com.project.it.service.MemberStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/it/organization")
public class OrganizationController {

    private final MemberStatusService memberSS;

    @GetMapping("")
    public List listRead(){      
        return memberSS.list();
    }

    @GetMapping("/page")
    public PageResponseDTO<MemberStatusDTO> pagingList(PageRequestDTO pageRequestDTO){
        return memberSS.getList(pageRequestDTO);
    }
}
