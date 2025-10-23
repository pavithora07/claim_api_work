package com.mypoc.ClaimApplication.controller;

import com.mypoc.ClaimApplication.dto.CountClaimRequest;
import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientmatch")
public class ClientMatchController {

    @Autowired
    private final ClaimService claimService;

    public ClientMatchController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping(
            value = "/CountClaim",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CountClaimResponse> countClaim(RequestEntity<CountClaimRequest> requestEntity) {
        CountClaimRequest request = requestEntity.getBody();
        CountClaimResponse response = claimService.countClaims(request);
        return ResponseEntity.ok(response);
    }
}

