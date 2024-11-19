package com.project.it.repository;

import com.project.it.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    @Query("select App from Application App order by App.no asc")
    Page<Application> selectList(Pageable pageable);

}
