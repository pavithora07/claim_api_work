package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.projection.ClaimCountProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 // your entity or mapped superclass
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    /**
     * Inline native query which returns rows:
     * [CLAIM_TYPE, STATUS_CODE, CLAIM_STREAM, CLAIM_COUNT, PENDED_FLAG]
     */
    @Query(value = """
    SELECT 
        CLAIM_TYPE as claimType,
        STATUS_CD as statusCd,
        COALESCE(CLAIM_STREAM, '') as claimStream,
        COUNT(*) as claimCount
    FROM CLAIM
    WHERE STATUS_CD = :statusCode
    GROUP BY CLAIM_TYPE, STATUS_CD, COALESCE(CLAIM_STREAM, '')
    """, nativeQuery = true)
    List<ClaimCountProjection> getClaimCountsByStatus(@Param("statusCode") Long statusCode);


}

