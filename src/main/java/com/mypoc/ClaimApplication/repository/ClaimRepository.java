package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClaimRepository extends JpaRepository<Claim, String> {

    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimType = :claimType AND c.statusCd = :statusCd")
    long countByClaimTypeAndStatusCd(@Param("claimType") String claimType, @Param("statusCd") String statusCd);
}
