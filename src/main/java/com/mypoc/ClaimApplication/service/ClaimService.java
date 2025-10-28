package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;

public interface ClaimService {
    CountClaimResponse getClaimCountsByClaimStatus();
}
