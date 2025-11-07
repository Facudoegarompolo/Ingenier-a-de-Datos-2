package com.example.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import com.example.entity.Medicion;
import java.util.UUID;

public interface MedicionRepository extends CassandraRepository<Medicion, UUID> {
    // Custom queries
}
