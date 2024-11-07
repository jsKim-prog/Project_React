package com.project.it.repository;

import com.project.it.domain.FileUpload;
import com.project.it.dto.FileUploadDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileLicenseRepository extends JpaRepository<FileUpload, Long> {
    //파일리스트 검색-category+assetNum
    @Query("select files from FileUpload files where files.category=:category and files.assetNum=:assetNum")
    List<FileUploadDTO> findAssetFileList(@Param("category") String category, @Param("assetNum") Long assetNum);

    //삭제처리
    @Query("update FileUpload file set file.deleteOrNot = :state where file.category=:category and file.assetNum=:assetNum")
    void updateDelState(@Param("category") String category, @Param("assetNum") Long assetNum, @Param("state")boolean state);
}
