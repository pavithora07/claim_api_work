package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.ClaimCounts;

public interface ClaimService {
    ClaimCounts getClaimCountsByClaimStatus();
}
