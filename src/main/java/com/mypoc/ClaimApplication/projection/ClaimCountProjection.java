package com.mypoc.ClaimApplication.dto;

public interface ClaimCountProjection {
    String getClaimType();
    Long getStatusCd();
    String getClaimStream();
    Integer getClaimCount();
    String getIsPended();
}
