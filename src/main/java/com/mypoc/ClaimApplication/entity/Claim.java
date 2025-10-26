package com.mypoc.ClaimApplication.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CLAIM")
@Data
public class Claim {
    @Id
    @Column(name = "MPI_CLAIM_ID")
    private String mpiClaimId;

    @Column(name = "CLAIM_TYPE")
    private String claimType;

    @Column(name = "STATUS_CD")
    private String statusCd;

    @Column(name = "CLAIM_STREAM")
    private String claimStream;

    // Add more columns if required
}
