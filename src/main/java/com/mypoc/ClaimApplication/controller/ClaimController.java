package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.service.ClaimService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clientmatch")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    /**
     * ✅ API 1: Count claims (with optional statusCd)
     * Example:
     *  - /countClaim?claimType=U&statusCd=100300000  → count by both
     *  - /countClaim?claimType=U                    → count all U-type claims
     */
    @GetMapping(
            value = "/countClaim",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CountClaimResponse> countClaim(
            @RequestParam String claimType,
            @RequestParam(required = false) String statusCd
    ) {
        CountClaimResponse response = claimService.countClaims(claimType, statusCd);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ API 2: Get all claims by claimType
     * Example:
     *  - /claims?claimType=U → returns all U claims and count
     */
    @GetMapping(
            value = "/claims",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Map<String, Object>> getClaimsByType(
            @RequestParam String claimType
    ) {
        List<Claim> claims = claimService.getClaimsByType(claimType);

        Map<String, Object> response = new HashMap<>();
        response.put("claimType", claimType);
        response.put("claimCount", claims.size());
        response.put("claims", claims);
        response.put("status", "SUCCESS");

        return ResponseEntity.ok(response);
    }
}
