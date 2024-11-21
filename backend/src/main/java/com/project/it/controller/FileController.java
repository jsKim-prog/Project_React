package com.project.it.controller;

import com.project.it.dto.FileUploadDTO;
import com.project.it.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@Log4j2
@RequestMapping("/dist/file")
@RequiredArgsConstructor
public class FileController { //file 다운로드 요청 처리
    private final CustomFileUtil fileUtil;

    @GetMapping("/{path}/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("path") String path, @PathVariable("filename") String filename){
        log.info("fileController 실행+++++++++++++ : "+path+"/"+filename);
        String savePath = path.replace("_", File.separator);
        FileUploadDTO dto = new FileUploadDTO();
        dto.setFolderPath(savePath);
        dto.setSaveFileName(filename);
        return fileUtil.getFile(dto);
    }



}
