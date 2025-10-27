package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ClaimService}.
 *
 * <p>Handles business logic for counting and fetching claim data,
 * delegating database interactions to {@link ClaimRepository}.
 */
@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Behavior:
     * <ul>
     *     <li>If both claimType and statusCd provided → counts matching records.</li>
     *     <li>If only claimType provided → counts all claims of that type.</li>
     * </ul>
     */
    @Override
    public CountClaimResponse countClaims(String claimType, String statusCd) {
        log.debug("Starting claim count operation | claimType={} | statusCd={}", claimType, statusCd);

        long count;
        if (statusCd != null && !statusCd.isEmpty()) {
            log.debug("Counting claims by claimType and statusCd...");
            count = claimRepository.countByClaimTypeAndStatusCd(claimType, statusCd);
        } else {
            log.debug("Counting claims only by claimType...");
            count = claimRepository.countByClaimType(claimType);
        }

        log.info("Claim count complete | claimType={} | statusCd={} | totalCount={}",
                claimType, statusCd, count);

        CountClaimResponse response = new CountClaimResponse();
        response.setClaimType(claimType);
        response.setStatusCd(statusCd);
        response.setClaimCount(count);
        response.setStatus("SUCCESS");

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Claim> getClaimsByType(String claimType) {
        log.debug("Fetching claims by claimType={}", claimType);
        List<Claim> claims = claimRepository.findByClaimType(claimType);
        log.info("Fetched {} claim(s) for claimType={}", claims.size(), claimType);
        return claims;
    }
}
