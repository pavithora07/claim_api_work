package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CountClaimResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountClaimResponse {
    private String claimType;
    private String statusCd;
    private long claimCount;
    private String status;
}
