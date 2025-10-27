package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for performing CRUD and custom query operations
 * on {@link Claim} entities.
 *
 * <p>This repository provides methods for:
 * <ul>
 *   <li>Counting claims by type and/or status</li>
 *   <li>Fetching all claims by type</li>
 *   <li>Retrieving grouped claim summary details</li>
 * </ul>
 *
 * <p>Query design follows JPA best practices with descriptive method names.
 *
 * @author
 * @since 2025-10
 */
public interface ClaimRepository extends JpaRepository<Claim, String> {

    // ------------------------------------------------------------------------
    // 1️⃣ Count Queries
    // ------------------------------------------------------------------------

    /**
     * Counts the total number of claims filtered by both claimType and statusCd.
     *
     * @param claimType the type of claim (e.g., "U")
     * @param statusCd  the claim status code (e.g., "100300000")
     * @return number of matching claims
     */
    @Query("""
           SELECT COUNT(c)
           FROM Claim c
           WHERE c.claimType = :claimType
             AND c.statusCd = :statusCd
           """)
    long countByClaimTypeAndStatusCd(@Param("claimType") String claimType,
                                     @Param("statusCd") String statusCd);

    /**
     * Counts the total number of claims filtered only by claimType.
     *
     * @param claimType the type of claim
     * @return number of matching claims
     */
    @Query("""
           SELECT COUNT(c)
           FROM Claim c
           WHERE c.claimType = :claimType
           """)
    long countByClaimType(@Param("claimType") String claimType);

    // ------------------------------------------------------------------------
    // 2️⃣ Data Retrieval Queries
    // ------------------------------------------------------------------------

    /**
     * Retrieves all claim records for a given claimType.
     *
     * @param claimType the type of claim to filter
     * @return list of matching {@link Claim} entities
     */
    List<Claim> findByClaimType(String claimType);

    // ------------------------------------------------------------------------
    // 3️⃣ Aggregation / Grouped Summary Queries
    // ------------------------------------------------------------------------

    /**
     * Retrieves grouped claim count details.
     * <p>
     * The result includes:
     * <ul>
     *   <li>Claim Type</li>
     *   <li>Status Code</li>
     *   <li>Claim Stream</li>
     *   <li>Count of claims in each group</li>
     * </ul>
     *
     * @return list of Object arrays — each element represents
     *         {claimType, statusCd, claimStream, count}
     */
    @Query("""
           SELECT c.claimType, c.statusCd, c.claimStream, COUNT(c)
           FROM Claim c
           GROUP BY c.claimType, c.statusCd, c.claimStream
           """)
    List<Object[]> findGroupedClaimCounts();
}
