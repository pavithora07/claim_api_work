package com.mypoc.ClaimApplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Represents a claim record in the system.
 *
 * <p>This entity maps to the CLAIM table and stores key claim details
 * such as type, status, and stream.
 *
 * Example:
 * <pre>
 * {
 *   "mpiClaimId": "123456",
 *   "claimType": "U",
 *   "statusCd": "100300000",
 *   "claimStream": "INPATIENT"
 * }
 * </pre>
 *
 * @author
 * @since 2025-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CLAIM")
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Primary key for the claim */
    @Id
    @Column(name = "MPI_CLAIM_ID", nullable = false, length = 50)
    private String mpiClaimId;

    /** Type of claim (e.g., U for Unmatched, O for Outpatient, etc.) */
    @Column(name = "CLAIM_TYPE", length = 20, nullable = false)
    private String claimType;

    /** Status code of the claim (e.g., 100300000) */
    @Column(name = "STATUS_CD", length = 20)
    private String statusCd;

    /** Stream of the claim (e.g., INPATIENT, OUTPATIENT) */
    @Column(name = "CLAIM_STREAM", length = 30)
    private String claimStream;

    // Future-proofing: Add more fields as needed (timestamps, source, etc.)
}
