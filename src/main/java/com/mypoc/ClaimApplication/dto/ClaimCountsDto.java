package com.mypoc.ClaimApplication.dto;

import com.mypoc.ClaimApplication.enums.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimCountsDto {

    private Long mpiClaimId;
    private String claimStream;
    private ClaimType claimType;
    private Integer claimCount;
    private String message;
}
