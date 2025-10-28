package com.mypoc.ClaimApplication.projection;

public interface ClaimCountProjection {
    String getClaimType();
    Long getStatusCd();
    String getClaimStream();
    Integer getClaimCount();
    String getIsPended();
}
