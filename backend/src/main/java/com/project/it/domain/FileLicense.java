package com.project.it.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileLicense { //라이선스 관련 증빙 파일
    private String fileName; //파일이름
    private String folderPath; //폴더경로
}

//    create table license_file_list (
//        license_id bigint not null,
//        file_name varchar(255),
//    folder_path varchar(255)
//    ) engine=InnoDB
