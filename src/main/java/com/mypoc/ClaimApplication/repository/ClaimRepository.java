package com.mypoc.ClaimApplication.repository;


import com.mypoc.ClaimApplication.dto.ClaimCounts;

public interface ClaimRepository {
    Boolean getClaimCountsByClaimStatus(ClaimCounts claimCounts);
}

