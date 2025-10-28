package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.dto.ClaimCounts;

public interface ClaimRepositoryCustom {
    boolean getClaimCountsByClaimStatus(ClaimCounts claimCounts);
}
