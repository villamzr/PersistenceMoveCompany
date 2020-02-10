package com.move.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.move.entity.MFile;

@Repository
public interface IFileRepository extends JpaRepository<MFile, String> {
}
