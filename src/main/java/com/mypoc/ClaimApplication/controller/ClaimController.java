package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.entity.ClaimCountDetail;
import com.mypoc.ClaimApplication.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller responsible for handling Claim-related REST endpoints.
 *
 * <p>This controller provides APIs for:
 * <ul>
 *     <li>Retrieving total claim count (optionally filtered by type and status)</li>
 *     <li>Fetching all claims by type</li>
 *     <li>Fetching grouped claim count details</li>
 * </ul>
 *
 * <p>Example Usage:
 * <pre>
 *     GET /api/v1/claims/count?claimType=U&statusCd=100300000
 *     GET /api/v1/claims?claimType=U
 *     GET /api/v1/claims/summary
 * </pre>
 *
 * @author
 * @since 2025-10
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // ------------------------------------------------------------------------
    // 1️⃣ API: Get total claim count by claim type and optional status code
    // ------------------------------------------------------------------------

    /**
     * Returns the total number of claims filtered by claimType and optionally by statusCd.
     *
     * @param claimType the type of claim (mandatory)
     * @param statusCd  the claim status code (optional)
     * @return JSON or XML response containing the count and claim details
     */
    @GetMapping(
            value = "/count",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CountClaimResponse> getClaimCount(
            @RequestParam String claimType,
            @RequestParam(required = false) String statusCd
    ) {
        log.info("[ClaimController] Counting claims | claimType={} | statusCd={}", claimType, statusCd);

        CountClaimResponse response = claimService.countClaims(claimType, statusCd);

        log.info("[ClaimController] Claim count fetched successfully | claimType={} | statusCd={} | totalCount={}",
                claimType, statusCd, response.getClaimCount());

        return ResponseEntity.ok(response);
    }

    // ------------------------------------------------------------------------
    // 2️⃣ API: Get all claim records by claim type
    // ------------------------------------------------------------------------

    /**
     * Retrieves all claims filtered by claimType.
     *
     * @param claimType the claim type to filter by (e.g., "U")
     * @return a response containing claim list, count, and metadata
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getClaimsByType(@RequestParam String claimType) {
        log.info("[ClaimController] Fetching claims for claimType={}", claimType);

        List<Claim> claims = claimService.getClaimsByType(claimType);

        Map<String, Object> response = new HashMap<>();
        response.put("claimType", claimType);
        response.put("totalClaims", claims.size());
        response.put("claims", claims);
        response.put("status", "SUCCESS");

        log.info("[ClaimController] Retrieved {} claim(s) for claimType={}", claims.size(), claimType);

        return ResponseEntity.ok(response);
    }

    // ------------------------------------------------------------------------
    // 3️⃣ API: Get grouped claim counts by type, status, and stream
    // ------------------------------------------------------------------------

    /**
     * Retrieves grouped claim count details (e.g., count by type, status, and stream).
     *
     * @return a list of ClaimCountDetail objects containing grouped count data
     */
    @GetMapping("/summary")
    public ResponseEntity<List<ClaimCountDetail>> getClaimSummary() {
        log.info("[ClaimController] Fetching grouped claim count summary...");

        List<ClaimCountDetail> claimSummary = claimService.getAllClaimCounts();

        log.info("[ClaimController] Grouped claim count summary retrieved successfully | totalGroups={}",
                claimSummary.size());

        return ResponseEntity.ok(claimSummary);
    }
}
