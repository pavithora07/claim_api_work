package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for {@link Claim} entity operations.
 *
 * <p>Provides custom queries for counting and filtering claims
 * using claimType and statusCd parameters.
 */
public interface ClaimRepository extends JpaRepository<Claim, String> {

    /**
     * Count all claims filtered by both claimType and statusCd.
     */
    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimType = :claimType AND c.statusCd = :statusCd")
    long countByClaimTypeAndStatusCd(@Param("claimType") String claimType,
                                     @Param("statusCd") String statusCd);

    /**
     * Count all claims filtered only by claimType.
     */
    @Query("SELECT COUNT(c) FROM Claim c WHERE c.claimType = :claimType")
    long countByClaimType(@Param("claimType") String claimType);

    /**
     * Retrieve all claims by claimType.
     */
    List<Claim> findByClaimType(String claimType);
}
git