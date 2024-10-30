package com.project.it.repository;

import com.project.it.domain.ManageSoftware;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssetSoftwareRepository extends JpaRepository<ManageSoftware, Long> {
    //C, U : save

    //R_one : 하나의 sw 등록정보 읽기
    @EntityGraph(attributePaths = "license")
    @Query("select sw from ManageSoftware sw where sw.id=:id")
    Optional<ManageSoftware> readOne(@Param("id") Long id);

    //D: 삭제 -> 삭제처리

}
