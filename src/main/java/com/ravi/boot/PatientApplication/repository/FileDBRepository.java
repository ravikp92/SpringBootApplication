package com.ravi.boot.PatientApplication.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ravi.boot.PatientApplication.model.FileModel;

@Repository
public interface FileDBRepository extends JpaRepository<FileModel, String> {

}