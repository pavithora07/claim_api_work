package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.ClaimCounts;
import com.mypoc.ClaimApplication.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for exposing Claim-related endpoints.
 *
 * <p>This controller provides access to ClaimCounts data grouped by
 * claim stream and type (pended vs non-pended).</p>
 */
@Slf4j
@RestController
@RequestMapping("/clientmatch")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    /**
     * Fetches ECM Claim counts grouped by ClaimStream and ClaimType.
     *
     * <p>Example endpoint:</p>
     * <pre>
     *     GET /clientmatch/ecmclaims
     * </pre>
     *
     * @return JSON structure containing counts for pended and non-pended claims.
     */
    @GetMapping("/ecmclaims")
    public ResponseEntity<ClaimCounts> getEcmClaims() {
        log.info("Received request for ECM claim counts.");

        ClaimCounts claimCounts = claimService.getClaimCountsByClaimStatus();

        if (claimCounts == null) {
            log.warn("No claim counts found for ECM claim status.");
            return ResponseEntity.noContent().build();
        }

        log.info("Successfully retrieved ECM claim counts with {} streams.",
                claimCounts.getClaimStreams().size());

        return ResponseEntity.ok(claimCounts);
    }
}
