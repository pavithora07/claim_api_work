package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public CountClaimResponse countClaims(String claimType, String statusCd) {
        long count;

        // Case 1: Both claimType & statusCd provided
        if (statusCd != null && !statusCd.isEmpty()) {
            count = claimRepository.countByClaimTypeAndStatusCd(claimType, statusCd);
        }
        // Case 2: Only claimType provided → count all matching claimType
        else {
            count = claimRepository.countByClaimType(claimType);
        }

        CountClaimResponse response = new CountClaimResponse();
        response.setClaimType(claimType);
        response.setStatusCd(statusCd);
        response.setClaimCount(count);
        response.setStatus("SUCCESS");
        return response;
    }

    @Override
    public List<Claim> getClaimsByType(String claimType) {
        return claimRepository.findByClaimType(claimType);
    }
}
