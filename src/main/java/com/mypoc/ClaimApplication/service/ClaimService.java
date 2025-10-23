package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimRequest;
import com.mypoc.ClaimApplication.dto.CountClaimResponse;

public interface ClaimService {
    public CountClaimResponse countClaims(CountClaimRequest request);
}
