package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;

import java.util.List;

public interface ClaimService {

    /**
     * Count and optionally list claims based on claimType and statusCd.
     *
     * @param claimType the type of claim (mandatory)
     * @param statusCd  optional status code filter
     * @return response containing claimType, statusCd, count, and status message
     */
    CountClaimResponse countClaims(String claimType, String statusCd);
    // ✅ Fetch all claims of a particular claimType
    List<Claim> getClaimsByType(String claimType);
}
