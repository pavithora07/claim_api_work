package com.mypoc.ClaimApplication.dto;

import com.mypoc.ClaimApplication.enums.ClaimType;
import lombok.Data;

@Data
public class ClaimCountsDto {
    private String claimStream;
    private ClaimType claimType;
    private Long statusCode;
    private Integer claimCount;
    private String miscText;

    //Static factory method for cleaner object creation
    public static ClaimCountsDto of(String stream, Long statusCode, ClaimType type, String miscText) {
        ClaimCountsDto dto = new ClaimCountsDto();
        dto.setClaimStream(stream);
        dto.setStatusCode(statusCode);
        dto.setClaimType(type);
        dto.setClaimCount(0);
        dto.setMiscText(miscText);
        return dto;
    }
}
