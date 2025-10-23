package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "countClaimRequest")
public class CountClaimRequest {

    @JacksonXmlProperty(localName = "clientCode")
    private String clientCode;

    @JacksonXmlProperty(localName = "filters")
    private ClaimFilter filters;

    // Getters and Setters
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public ClaimFilter getFilters() {
        return filters;
    }

    public void setFilters(ClaimFilter filters) {
        this.filters = filters;
    }
}
