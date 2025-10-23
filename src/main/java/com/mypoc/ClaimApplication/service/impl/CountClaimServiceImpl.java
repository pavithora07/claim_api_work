package com.mypoc.ClaimApplication.service.impl;

import com.mypoc.ClaimApplication.dto.CountClaimRequest;
import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import com.mypoc.ClaimApplication.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.LocalDate;

@Service
public class CountClaimServiceImpl {

    private final ClaimRepository claimRepository;
    private final ClientRepository clientRepository;

    public CountClaimServiceImpl(ClaimRepository claimRepository, ClientRepository clientRepository) {
        this.claimRepository = claimRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public CountClaimResponse countClaims(CountClaimRequest request) {
        var client = clientRepository.findByClientCode(request.getClientCode())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found")
                );

        // use LocalDate directly (not OffsetDateTime)
        String claimType = request.getFilters() != null ? request.getFilters().getClaimType() : null;
        String status = request.getFilters() != null ? request.getFilters().getStatus() : null;
        LocalDate fromDate = request.getFilters() != null ? request.getFilters().getFromDate() : null;
        LocalDate toDate = request.getFilters() != null ? request.getFilters().getToDate() : null;

        long count = claimRepository.countClaims(
                client.getId(),
                claimType,
                status,
                fromDate,
                toDate
        );

        CountClaimResponse response = new CountClaimResponse();
        response.setClientCode(client.getClientCode());
        response.setTotalClaims(count);
        response.setFiltersApplied(request.getFilters());
        response.setTimestamp(OffsetDateTime.now());

        return response;
    }
}
