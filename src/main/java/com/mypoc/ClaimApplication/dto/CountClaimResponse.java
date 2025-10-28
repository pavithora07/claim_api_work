package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "CountClaimResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountClaimResponse {

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "message")
    private String message;

    @JacksonXmlElementWrapper(localName = "claimCounts")
    @JacksonXmlProperty(localName = "claimCount")
    private List<ClaimCountsDto> claimCounts;  // flattened list
}
