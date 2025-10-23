package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.time.OffsetDateTime;

@JacksonXmlRootElement(localName = "countClaimResponse")
public class CountClaimResponse {

    @JacksonXmlProperty(localName = "clientCode")
    private String clientCode;

    @JacksonXmlProperty(localName = "totalClaims")
    private long totalClaims;

    @JacksonXmlProperty(localName = "filtersApplied")
    private ClaimFilter filtersApplied;

    @JacksonXmlProperty(localName = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") // ISO format, works for both JSON/XML
    private OffsetDateTime timestamp;

    // Getters and Setters
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public long getTotalClaims() {
        return totalClaims;
    }

    public void setTotalClaims(long totalClaims) {
        this.totalClaims = totalClaims;
    }

    public ClaimFilter getFiltersApplied() {
        return filtersApplied;
    }

    public void setFiltersApplied(ClaimFilter filtersApplied) {
        this.filtersApplied = filtersApplied;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
