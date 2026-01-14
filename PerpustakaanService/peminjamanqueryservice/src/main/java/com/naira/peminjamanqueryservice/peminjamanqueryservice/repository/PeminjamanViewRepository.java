package com.naira.peminjamanqueryservice.peminjamanqueryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.naira.peminjamanqueryservice.peminjamanqueryservice.model.PeminjamanView;

@Repository
public interface PeminjamanViewRepository extends MongoRepository<PeminjamanView, Long> {
}