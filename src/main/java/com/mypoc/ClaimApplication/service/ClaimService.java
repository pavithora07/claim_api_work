package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;

import java.util.List;

/**
 * Defines business operations for Claims.
 *
 * <p>Responsibilities include counting and retrieving claim records
 * based on input criteria.
 */
public interface ClaimService {

    /**
     * Counts total claims filtered by claimType and optionally statusCd.
     *
     * @param claimType Claim type filter (mandatory)
     * @param statusCd  Status code filter (optional)
     * @return Aggregated {@link CountClaimResponse} with claim count and summary details
     */
    CountClaimResponse countClaims(String claimType, String statusCd);

    /**
     * Retrieves all claims based on claimType.
     *
     * @param claimType Claim type filter
     * @return List of {@link Claim} entities
     */
    List<Claim> getClaimsByType(String claimType);
}
