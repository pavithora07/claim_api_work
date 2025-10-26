package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.service.ClaimService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientmatch")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

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
}
