package com.mypoc.ClaimApplication.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO representing aggregated claim count details.
 *
 * <p>This object is typically returned from APIs or services
 * that summarize claim records by claimType, statusCd, and claimStream.
 *
 * <p>Supports both JSON and XML serialization formats.
 *
 * Example JSON response:
 * <pre>
 * {
 *   "claimType": "U",
 *   "statusCd": "100300000",
 *   "claimStream": "INPATIENT",
 *   "claimCount": 42,
 *   "miscText": "Pended U Claims Requiring Manual Match"
 * }
 * </pre>
 *
 * Example XML response:
 * <pre>
 * <ClaimCountDetail>
 *   <claimType>U</claimType>
 *   <statusCd>100300000</statusCd>
 *   <claimStream>INPATIENT</claimStream>
 *   <claimCount>42</claimCount>
 *   <miscText>Pended U Claims Requiring Manual Match</miscText>
 * </ClaimCountDetail>
 * </pre>
 *
 * @author
 * @since 2025-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClaimCountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Type of claim (e.g., "U", "O", etc.) */
    @JacksonXmlProperty(localName = "claimType")
    private String claimType;

    /** Status code of the claim (e.g., "100300000") */
    @JacksonXmlProperty(localName = "statusCd")
    private String statusCd;

    /** Stream of the claim (e.g., "INPATIENT", "OUTPATIENT") */
    @JacksonXmlProperty(localName = "claimStream")
    private String claimStream;

    /** Total number of claims for the given grouping */
    @JacksonXmlProperty(localName = "claimCount")
    private Long claimCount;

    /** Miscellaneous descriptive text, e.g., "Pended Claims Requiring Manual Match" */
    @JacksonXmlProperty(localName = "miscText")
    private String miscText;
}
