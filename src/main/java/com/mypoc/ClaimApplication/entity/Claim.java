package com.mypoc.ClaimApplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CLAIM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @Column(name = "MPI_CLAIM_ID", nullable = false)
    private Long mpiClaimId;

    @Column(name = "CLIENT_CLAIM_ID")
    private String clientClaimId;

    @Column(name = "CLAIM_STREAM")
    private String claimStream;

    @Column(name = "CLAIM_TYPE")
    private String claimType;

    @Column(name = "STATUS_CD")
    private Long statusCd;

    @Column(name = "MISC_TEXT")
    private String miscText;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;
}
