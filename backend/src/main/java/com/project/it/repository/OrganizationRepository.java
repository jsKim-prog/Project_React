package com.project.it.repository;

import com.project.it.domain.Member;
import com.project.it.domain.Organization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

    @EntityGraph(attributePaths = {"organizationTeamList"})
    Organization findByMemberMno (@Param("mno") Long mno);


}
