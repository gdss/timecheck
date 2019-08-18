package com.gdss.timecheck.repositories;

import com.gdss.timecheck.models.Clockin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClockinRepository extends JpaRepository<Clockin, UUID>, JpaSpecificationExecutor {
}
