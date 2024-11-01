package com.project.it.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {
    private Long fno;

    private String category; //구분(폴더)
    private Long assetNum; //카테고리_asset id
    private String originFileName; //원본파일이름
    private String saveFileName; //저장 파일 이름(uuid)
    private String folderPath; //저장경로
    private Long size; //파일크기
    private boolean deleteOrNot; //삭제여부

}