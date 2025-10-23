package com.mypoc.ClaimApplication.entity;


import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clients", indexes = {
        @Index(name = "idx_clients_client_code", columnList = "client_code")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "client_code", nullable = false, unique = true, length = 50)
    private String clientCode;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // convenience constructor
    public ClientEntity(String clientCode, String fullName, String email, String phone) {
        this.clientCode = clientCode;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }
}

