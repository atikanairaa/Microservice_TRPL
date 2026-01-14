package com.naira.bukuservice.bukuservice.repository;

import com.naira.bukuservice.bukuservice.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
}