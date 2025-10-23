package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {

    @Query("""
        SELECT COUNT(c)
        FROM ClaimEntity c
        WHERE c.client.id = :clientId
        AND (:claimType IS NULL OR c.claimType = :claimType)
        AND (:status IS NULL OR c.status = :status)
        AND (:fromDate IS NULL OR c.submittedDate >= :fromDate)
        AND (:toDate IS NULL OR c.submittedDate <= :toDate)
    """)
    long countClaims(
            @Param("clientId") Long clientId,
            @Param("claimType") String claimType,
            @Param("status") String status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
