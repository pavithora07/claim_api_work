package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, String> {

    //  Case 1: Count when both claimType and statusCd are provided
    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimType = :claimType AND c.statusCd = :statusCd")
    long countByClaimTypeAndStatusCd(@Param("claimType") String claimType, @Param("statusCd") String statusCd);

    //  Case 2: Count when only claimType is provided (statusCd ignored)
    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimType = :claimType")
    long countByClaimType(@Param("claimType") String claimType);

    //  Retrieve all claims of given claimType
    List<Claim> findByClaimType(String claimType);
}
