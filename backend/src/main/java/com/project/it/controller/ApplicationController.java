package com.project.it.controller;

import com.project.it.dto.*;
import com.project.it.service.ApplicationService;
import com.project.it.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/it/application")
public class ApplicationController {
    private final ApplicationService appS;
    private final CustomFileUtil fileUtil;

    @GetMapping("/page")
    public PageResponseDTO<ApplicationDTO> pagingAppList(PageRequestDTO pageRequestDTO){
        return appS.getList(pageRequestDTO);
    }

    @PostMapping("/register")
    public Map<String, String> register(ApplicationDTO applicationDTO) {
        log.info("등록컨트롤러 시작");
        log.info("register : " + applicationDTO);

        List<String> uploadFileNames = fileUtil.saveFiles(applicationDTO.getFiles());  // 파일 저장 처리
        log.info(uploadFileNames);

        applicationDTO.setUploadFileNames(uploadFileNames);

        Long msg = appS.register(applicationDTO);  // 등록 처리

        log.info(msg);
        return Map.of("RESULT", msg.toString());
    }

    @GetMapping("/getOne/{mno}")
    public ApplicationDTO oneRead(@PathVariable(name = "mno") String mno){
        ApplicationDTO applicationDTO = appS.getOne(mno);
        return applicationDTO;
    }

    @Value("${upload.path}")  // application.properties에서 경로 설정
    private String uploadPath;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getFile/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(fileName); // 실제 경로로 파일 찾기
            Resource resource = new FileSystemResource(filePath);

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // 이미지 파일이라면 MIME 타입 설정
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
