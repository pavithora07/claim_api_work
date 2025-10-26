package com.mypoc.ClaimApplication.service;


import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import org.springframework.stereotype.Service;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public CountClaimResponse countClaims(String claimType, String statusCd) {
        long count = claimRepository.countByClaimTypeAndStatusCd(claimType, statusCd);

        CountClaimResponse response = new CountClaimResponse();
        response.setClaimType(claimType);
        response.setStatusCd(statusCd);
        response.setClaimCount(count);
        response.setStatus("SUCCESS");
        return response;
    }
}
