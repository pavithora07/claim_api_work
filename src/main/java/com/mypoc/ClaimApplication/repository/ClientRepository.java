package com.mypoc.ClaimApplication.repository;


import java.util.Optional;

import com.mypoc.ClaimApplication.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientCode(String clientCode);
    Optional<ClientEntity> findByExternalId(java.util.UUID externalId);
}
