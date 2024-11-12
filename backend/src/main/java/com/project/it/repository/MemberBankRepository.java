package com.project.it.repository;

import com.project.it.domain.MemberBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBankRepository extends JpaRepository<MemberBank, String> {
}
