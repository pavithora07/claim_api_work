package com.mypoc.ClaimApplication.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "claims", indexes = {
        @Index(name = "idx_claims_client", columnList = "client_id"),
        @Index(name = "idx_claims_status", columnList = "status"),
        @Index(name = "idx_claims_type_status", columnList = "claim_type, status"),
        @Index(name = "idx_claims_submitted_date", columnList = "submitted_date")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClaimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Column(name = "claim_type", nullable = false, length = 50)
    private String claimType;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "INR";

    @Column(name = "submitted_date", nullable = false)
    private OffsetDateTime submittedDate = OffsetDateTime.now();

    @Column(name = "resolved_date")
    private OffsetDateTime resolvedDate;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // convenience constructor
    public ClaimEntity(ClientEntity client, String claimType, String status, BigDecimal amount, String currency) {
        this.client = client;
        this.claimType = claimType;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
    }
}
