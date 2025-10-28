package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {

    @Query("""
        SELECT c.claimStream, c.claimType, COUNT(c.mpiClaimId)
        FROM Claim c
        GROUP BY c.claimStream, c.claimType
        ORDER BY c.claimStream ASC
    """)
    List<Object[]> getAllClaimCounts();
}
