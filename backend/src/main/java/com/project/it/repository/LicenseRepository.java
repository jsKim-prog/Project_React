package com.project.it.repository;

import com.project.it.domain.AssetLicense;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends JpaRepository<AssetLicense, Long> {
    //C:라이선스 등록
    //R_one : 라이선스 개별 조회(+ 파일 전체 불러오기)
    @EntityGraph(attributePaths = "fileList")
    @Query("select li from AssetLicense li where li.id=:lno")
    Optional<AssetLicense> readOne(@Param("lno") Long lno);

    //R_all : 라이선스 리스트 조회(License, filer 개수)
    @Query("select li, count(list) from AssetLicense li left join li.fileList list")
    List<AssetLicense> readList(Pageable pageable);



}
