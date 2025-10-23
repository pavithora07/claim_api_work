package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;

@JacksonXmlRootElement(localName = "ClaimFilter")
public class ClaimFilter {

    @JacksonXmlProperty(localName = "claimType")
    private String claimType;

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "fromDate")
    @JsonFormat(pattern = "yyyy-MM-dd") // ensures consistent parsing for JSON & XML
    private LocalDate fromDate;

    @JacksonXmlProperty(localName = "toDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    // Getters and Setters
    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
