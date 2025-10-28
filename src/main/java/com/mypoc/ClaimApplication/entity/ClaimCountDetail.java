package com.mypoc.ClaimApplication.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClaimCountDetail {

    @JacksonXmlProperty(localName = "claimType")
    private String claimType;

    @JacksonXmlProperty(localName = "statusCd")
    private String statusCd;

    @JacksonXmlProperty(localName = "claimStream")
    private String claimStream;

    @JacksonXmlProperty(localName = "claimCount")
    private long claimCount;
}
