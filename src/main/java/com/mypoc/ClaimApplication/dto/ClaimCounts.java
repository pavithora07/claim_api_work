package com.mypoc.ClaimApplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypoc.ClaimApplication.enums.ClaimType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 *
 * <p>Maintains a structured count of claims grouped by stream and type.
 * Supports both <b>normal</b> and <b>pended</b> claims.</p>
 *
 * <ul>
 *   <li>claimsMap → Holds non-pended claims</li>
 *   <li>pendedClaimsMap → Holds pended claims</li>
 * </ul>
 *
 * <p>Automatically initializes zero-count entries for each (ClaimStream, ClaimType) pair.</p>
 * <p> Note: Not thread-safe (intended for single-request use only)</p>
 */
@Getter
@Slf4j
public class ClaimCounts implements Serializable {

    private static final long serialVersionUID = 476006948449110737L;

    // ---------- MAIN MAPS ----------
    private final LinkedHashMap<String, Map<ClaimType, ClaimCountsDto>> claimsMap;
    private final LinkedHashMap<String, Map<ClaimType, ClaimCountsDto>> pendedClaimsMap;

    // ---------- SUPPORTING FIELDS ----------
    private final Set<String> claimStreams; // preserves insertion order

    @JsonIgnore
    private final Long statusCode;

    private final String initMessage; // e.g., "Claims Requiring Manual Match"
    private final boolean prefixMessageWithClaimType; // e.g., adds "HCFA " / "UB "

    // ---------- CONSTRUCTORS ----------

    public ClaimCounts() {
        this(new LinkedHashSet<>(), 0L, "", false);
    }

    public ClaimCounts(Set<String> initClaimStreams) {
        this(initClaimStreams, 0L, "", false);
    }

    public ClaimCounts(Set<String> initClaimStreams, Long statusCode, String initMessage, boolean prefixMessageWithClaimType) {
        this.claimsMap = new LinkedHashMap<>();
        this.pendedClaimsMap = new LinkedHashMap<>();
        this.claimStreams = new LinkedHashSet<>(Optional.ofNullable(initClaimStreams).orElse(Collections.emptySet()));
        this.statusCode = statusCode;
        this.initMessage = initMessage;
        this.prefixMessageWithClaimType = prefixMessageWithClaimType;

        initialize(this.claimStreams);

        log.debug(" ClaimCounts initialized with streams: {}", this.claimStreams);
    }

    // ---------- INITIALIZATION ----------

    /**
     * Initializes claim entries for each stream and claim type.
     * Handles both pended and non-pended maps.
     */
    private void initialize(Collection<String> streams) {
        for (boolean pended : List.of(false, true)) {
            Map<String, Map<ClaimType, ClaimCountsDto>> targetMap = getClaimsMap(pended);
            for (String stream : streams) {
                targetMap.put(stream, createTypeMap(stream, pended));
            }
        }
    }

    /**
     * Builds a type-based map (H/U) initialized with zero counts.
     */
    private Map<ClaimType, ClaimCountsDto> createTypeMap(String stream, boolean pended) {
        Map<ClaimType, ClaimCountsDto> typeMap = new LinkedHashMap<>();
        for (ClaimType ct : ClaimType.values()) {
            String prefix = buildMessagePrefix(ct, pended);
            typeMap.put(ct, buildInitialDto(stream, ct, prefix + initMessage));
        }
        return typeMap;
    }

    private ClaimCountsDto buildInitialDto(String claimStream, ClaimType claimType, String message) {
        return ClaimCountsDto.builder()
                .mpiClaimId(0L)
                .claimStream(claimStream)
                .claimType(claimType)
                .claimCount(0)
                .message(message)
                .build();
    }

    private String buildMessagePrefix(ClaimType ct, boolean pended) {
        if (!prefixMessageWithClaimType) return "";
        String typeLabel = (ct == ClaimType.H ? "HCFA " : "UB ");
        return pended ? ("Pended " + typeLabel) : typeLabel;
    }

    // ---------- CORE METHODS ----------

    /**
     * Updates or inserts claim count for a stream/type & pended flag.
     * Auto-initializes the stream if missing.
     */
    public ClaimCountsDto updateClaimCount(ClaimCountsDto claim, boolean pendedClaim) {
        LinkedHashMap<String, Map<ClaimType, ClaimCountsDto>> map = getClaimsMap(pendedClaim);

        // Initialize stream if missing
        if (!map.containsKey(claim.getClaimStream())) {
            addClaimStream(claim.getClaimStream());
            log.debug(" Initialized new claim stream: {}", claim.getClaimStream());
        }

        Map<ClaimType, ClaimCountsDto> claimTypeMap = map.get(claim.getClaimStream());
        ClaimCountsDto existing = claimTypeMap.get(claim.getClaimType());

        if (existing == null) {
            claimTypeMap.put(claim.getClaimType(), claim);
            return claim;
        }

        // Update existing values
        existing.setClaimCount(claim.getClaimCount());
        existing.setMessage(claim.getMessage());
        existing.setMpiClaimId(claim.getMpiClaimId());

        log.trace(" Updated claim [{}:{}] (pended={}) → count={}",
                claim.getClaimStream(), claim.getClaimType(), pendedClaim, claim.getClaimCount());

        return existing;
    }

    public ClaimCountsDto addClaimCount(ClaimCountsDto claim, boolean pendedClaim) {
        LinkedHashMap<String, Map<ClaimType, ClaimCountsDto>> map = getClaimsMap(pendedClaim);
        addClaimStream(claim.getClaimStream());
        return map.get(claim.getClaimStream()).put(claim.getClaimType(), claim);
    }

    public Map<ClaimType, ClaimCountsDto> getClaimCounts(String claimStream, boolean pendedClaim) {
        return getClaimsMap(pendedClaim).get(claimStream);
    }

    public ClaimCountsDto getClaimCount(String claimStream, ClaimType claimType, boolean pendedClaim) {
        Map<ClaimType, ClaimCountsDto> map = getClaimCounts(claimStream, pendedClaim);
        return map == null ? null : map.get(claimType);
    }

    public LinkedHashMap<String, Map<ClaimType, ClaimCountsDto>> getClaimsMap(boolean pendedClaim) {
        return pendedClaim ? pendedClaimsMap : claimsMap;
    }

    public void addClaimStream(String claimStream) {
        if (claimStream != null && claimStreams.add(claimStream)) {
            initialize(Collections.singleton(claimStream));
        }
    }

    public boolean removeClaimStream(String claimStream) {
        claimsMap.remove(claimStream);
        pendedClaimsMap.remove(claimStream);
        return claimStreams.remove(claimStream);
    }
}
