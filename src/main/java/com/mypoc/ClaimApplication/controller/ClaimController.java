package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.service.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clientmatch")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @Operation(summary = "Fetch claim counts grouped by claim type and stream")
    @GetMapping(
            value = "/ecmclaims",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CountClaimResponse> getEcmClaims() {
        log.info(" API Call: /clientmatch/ecmclaims initiated");

        try {
            CountClaimResponse response = claimService.getClaimCountsByClaimStatus();
            log.info(" Claim counts fetched successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error(" Error while fetching claim counts", ex);
            CountClaimResponse errorResponse = new CountClaimResponse();
            errorResponse.setStatus("FAILED");
            errorResponse.setMessage("Error fetching claim counts: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
