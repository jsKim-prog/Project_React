package com.project.it.repository;

import com.project.it.domain.ManageComputer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetComputerRepository extends JpaRepository<ManageComputer, Long> {
}