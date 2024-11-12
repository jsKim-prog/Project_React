package com.project.it.repository;

import com.project.it.domain.Member;
import com.project.it.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStatusRepository extends JpaRepository<MemberStatus, String> {

    MemberStatus findByMemberMno(Long mno);



}