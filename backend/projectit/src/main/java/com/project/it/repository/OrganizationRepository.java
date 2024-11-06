package com.project.it.repository;

import com.project.it.domain.MemberStatus;
import com.project.it.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

    @Query("select org from Organization org where org.mno = :mno")
    Organization getStatus(@Param("mno") int mno);
}
