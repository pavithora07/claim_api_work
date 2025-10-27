package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.entity.ClaimCountDetail;

import java.util.List;

/**
 * Service interface defining core business operations for managing and analyzing Claims.
 *
 * <p>This service layer abstracts claim-related logic and acts as a bridge
 * between the controller and the repository, ensuring clean separation of concerns.
 *
 * <p><b>Responsibilities include:</b>
 * <ul>
 *   <li>Counting claims based on type and/or status</li>
 *   <li>Retrieving claim details by type</li>
 *   <li>Generating grouped claim count summaries</li>
 * </ul>
 *
 * <p>All methods in this service are intended to be implemented by
 * {@link com.mypoc.ClaimApplication.service.ClaimServiceImpl}.
 *
 * @author
 * @since 2025-10
 */
public interface ClaimService {

    // ------------------------------------------------------------------------
    // 1️⃣ Count Operations
    // ------------------------------------------------------------------------

    /**
     * Counts total number of claims filtered by claimType and optionally by statusCd.
     *
     * <p><b>Behavior:</b>
     * <ul>
     *   <li>If both {@code claimType} and {@code statusCd} are provided, returns the count matching both filters.</li>
     *   <li>If only {@code claimType} is provided, returns the total count for that type.</li>
     * </ul>
     *
     * @param claimType the claim type to filter (required)
     * @param statusCd  the claim status code (optional)
     * @return {@link CountClaimResponse} containing total count and metadata
     */
    CountClaimResponse countClaims(String claimType, String statusCd);

    // ------------------------------------------------------------------------
    // 2️⃣ Retrieval Operations
    // ------------------------------------------------------------------------

    /**
     * Retrieves all claim records filtered by claimType.
     *
     * @param claimType the claim type to filter (e.g., "U", "I")
     * @return list of {@link Claim} entities
     */
    List<Claim> getClaimsByType(String claimType);

    // ------------------------------------------------------------------------
    // 3️⃣ Aggregation Operations
    // ------------------------------------------------------------------------

    /**
     * Retrieves grouped claim count summaries combining
     * claimType, statusCd, and claimStream dimensions.
     *
     * @return list of {@link ClaimCountDetail} containing grouped count details
     */
    List<ClaimCountDetail> getAllClaimCounts();
}
