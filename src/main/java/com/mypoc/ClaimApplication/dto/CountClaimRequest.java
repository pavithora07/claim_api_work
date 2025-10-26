package com.mypoc.ClaimApplication.dto;

import lombok.Data;

@Data
public class CountClaimRequest {
    private String clientCd;
    private String statusCd;
    private String claimType;
}
