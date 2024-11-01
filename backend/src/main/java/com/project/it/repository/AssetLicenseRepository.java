package com.project.it.repository;

import com.project.it.domain.AssetLicense;
import com.project.it.dto.AssetLicenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetLicenseRepository extends JpaRepository<AssetLicense, Long> {
    //R_one : 라이선스 정보 하나만 가져오기 + file 리스트
    @Query("select asset, files from AssetLicense asset left join FileUpload files on files.category='license' and files.assetNum=asset.ano where asset.ano=:ano")
    AssetLicenseDTO findByAnoWithFiles(@Param("ano") Long ano);

    //R_all : 라이선스(asset) 리스트+ file 개수
    @Query("select asset, count(files) from AssetLicense asset left join FileUpload files on files.category='license' and files.assetNum=asset.ano where asset.deleteOrNot=true and files.deleteOrNot=true ")
    Page<Object[]> getList(Pageable pageable);

    //D: 라이선스 삭제_삭제처리(리스트에서 보이지 않게)
    @Query("update AssetLicense asset set asset.deleteOrNot=:state where asset.ano=:ano")
    void updateToDelete(@Param("ano")Long ano, @Param("state")boolean state);
}
