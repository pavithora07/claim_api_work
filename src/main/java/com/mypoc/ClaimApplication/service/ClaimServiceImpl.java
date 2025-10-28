package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.dto.ClaimCounts;
import com.mypoc.ClaimApplication.dto.ClaimCountsDto;
import com.mypoc.ClaimApplication.repository.ClaimRepositoryCustom;
import com.mypoc.ClaimApplication.utils.EcmServicesConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepositoryCustom claimRepository;

    public ClaimServiceImpl(ClaimRepositoryCustom claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public CountClaimResponse getClaimCountsByClaimStatus() {
        log.info(" Fetching claim counts grouped by claim status...");

        //  Create an empty ClaimCounts model (no pre-initialization)
        ClaimCounts claimCounts = new ClaimCounts(
                new LinkedHashSet<>(),
                EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH,
                "Claims Requiring Manual Match",
                true
        );

        //  Fetch & populate from DB
        boolean containsBlankStream = claimRepository.getClaimCountsByClaimStatus(claimCounts);

        //  Flatten populated data for response
        List<ClaimCountsDto> flatList = claimCounts.getClaimsMap(false)
                .values()
                .stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());

        //  Prepare unified API response
        CountClaimResponse response = new CountClaimResponse();
        response.setStatus("SUCCESS");
        response.setMessage("Claim counts fetched successfully");
        response.setClaimCounts(flatList);

        log.info(" {} claim records fetched successfully. Blank stream present: {}",
                flatList.size(), containsBlankStream);

        return response;
    }
}
