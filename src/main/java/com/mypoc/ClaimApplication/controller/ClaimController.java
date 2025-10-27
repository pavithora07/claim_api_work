package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller responsible for handling Claim-related endpoints.
 *
 * <p>Provides APIs to:
 * <ul>
 *   <li>Count claims by type and/or status</li>
 *   <li>Retrieve all claims for a given claim type</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 *   GET /api/v1/clientmatch/countClaim?claimType=U&statusCd=100300000
 *   GET /api/v1/clientmatch/claims?claimType=U
 * </pre>
 *
 * @author
 * @since 2025-10
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/clientmatch")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    /**
     * Count total number of claims filtered by claimType and optionally statusCd.
     *
     * @param claimType Claim type filter (e.g., "U")
     * @param statusCd  Optional status code filter (e.g., "100300000")
     * @return Response containing total count and claimType details
     */
    @GetMapping(
            value = "/countClaim",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CountClaimResponse> countClaim(
            @RequestParam String claimType,
            @RequestParam(required = false) String statusCd
    ) {
        log.info("Received request to count claims | claimType={} | statusCd={}", claimType, statusCd);

        CountClaimResponse response = claimService.countClaims(claimType, statusCd);

        log.info("Claim count retrieved successfully | claimType={} | statusCd={} | count={}",
                claimType, statusCd, response.getClaimCount());

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all claims based on provided claimType.
     *
     * @param claimType Claim type (e.g., "U")
     * @return Response with claim list and metadata
     */
    @GetMapping("/claims")
    public ResponseEntity<Map<String, Object>> getClaimsByType(@RequestParam String claimType) {
        log.info("Fetching all claims for claimType={}", claimType);

        List<Claim> claims = claimService.getClaimsByType(claimType);
        Map<String, Object> response = new HashMap<>();

        response.put("claimType", claimType);
        response.put("claimCount", claims.size());
        response.put("claims", claims);
        response.put("status", "SUCCESS");

        log.info("Retrieved {} claim(s) for claimType={}", claims.size(), claimType);

        return ResponseEntity.ok(response);
    }
}
