package com.mypoc.ClaimApplication.dto;

import com.mypoc.ClaimApplication.enums.ClaimType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 *  Modern, maintainable, and type-safe ClaimCounts class.
 * Tracks claim counts for both normal and pended claims,
 * grouped by ClaimStream and ClaimType.
 */
@Getter
@Slf4j
public class ClaimCounts implements Serializable {

    private static final long serialVersionUID = 476006948449110737L;

    // 🔹 Primary data holders
    private final Map<String, Map<ClaimType, ClaimCountsDto>> claimsMap;
    private final Map<String, Map<ClaimType, ClaimCountsDto>> pendedClaimsMap;

    // 🔹 Streams and initialization details
    private final Set<String> claimStreams;
    private final Long statusCode;
    private final String initMiscText;
    private final boolean prefixMiscTextWithClaimType;

    // 🔹 Constants
    private static final boolean[] CLAIM_STATUSES = {false, true};

    // ------------------------------------------------------------
    // 🔸 Constructors
    // ------------------------------------------------------------

    public ClaimCounts() {
        this(new LinkedHashSet<>(), 0L, "", false);
    }

    public ClaimCounts(Set<String> initClaimStreams) {
        this(initClaimStreams, 0L, "", false);
    }

    @Builder
    public ClaimCounts(Set<String> initClaimStreams,
                       Long statusCode,
                       String initMiscText,
                       boolean prefixMiscTextWithClaimType) {

        this.claimsMap = new LinkedHashMap<>();
        this.pendedClaimsMap = new LinkedHashMap<>();
        this.claimStreams = new LinkedHashSet<>(initClaimStreams);
        this.statusCode = statusCode;
        this.initMiscText = initMiscText;
        this.prefixMiscTextWithClaimType = prefixMiscTextWithClaimType;

        initialize(initClaimStreams);

        if (log.isDebugEnabled()) {
            log.debug("✅ ClaimCounts initialized with {} streams, {} claim types per stream",
                    initClaimStreams.size(), ClaimType.values().length);
        }
    }

    // ------------------------------------------------------------
    // 🔸 Initialization Logic
    // ------------------------------------------------------------

    private void initialize(Collection<String> streams) {
        for (boolean pended : CLAIM_STATUSES) {
            Map<String, Map<ClaimType, ClaimCountsDto>> targetMap = getClaimsMap(pended);

            streams.forEach(stream -> {
                Map<ClaimType, ClaimCountsDto> claimTypeMap = new LinkedHashMap<>();

                for (ClaimType type : ClaimType.values()) {
                    String prefix = prefixMiscTextWithClaimType ? buildPrefix(pended, type) : "";
                    claimTypeMap.put(type, ClaimCountsDto.of(stream, statusCode, type, prefix + initMiscText));
                }

                targetMap.put(stream, claimTypeMap);
            });
        }
    }

    // ------------------------------------------------------------
    // 🔸 Core Functional Methods
    // ------------------------------------------------------------

    private String buildPrefix(boolean pended, ClaimType type) {
        String base = (type == ClaimType.H) ? "HCFA " : "UB ";
        return pended ? "Pended " + base : base;
    }

    public ClaimCountsDto getClaimCount(ClaimType claimType, String claimStream, boolean pendedClaim) {
        return Optional.ofNullable(getClaimsMap(pendedClaim)
                        .getOrDefault(claimStream, Collections.emptyMap())
                        .get(claimType))
                .orElse(null);
    }

    public Map<ClaimType, ClaimCountsDto> getClaimCounts(String claimStream, boolean pendedClaim) {
        return getClaimsMap(pendedClaim)
                .getOrDefault(claimStream, Collections.emptyMap());
    }

    public ClaimCountsDto addOrUpdateClaimCount(ClaimCountsDto claim, boolean pendedClaim) {
        Map<String, Map<ClaimType, ClaimCountsDto>> targetMap = getClaimsMap(pendedClaim);
        claimStreams.add(claim.getClaimStream());

        targetMap.computeIfAbsent(claim.getClaimStream(), k -> new LinkedHashMap<>())
                .put(claim.getClaimType(), claim);

        return claim;
    }

    public ClaimCountsDto updateClaimCount(ClaimCountsDto claim, boolean pendedClaim) {
        Map<ClaimType, ClaimCountsDto> typeMap = getClaimsMap(pendedClaim)
                .computeIfAbsent(claim.getClaimStream(), k -> new LinkedHashMap<>());

        ClaimCountsDto existing = typeMap.get(claim.getClaimType());
        if (existing == null) {
            typeMap.put(claim.getClaimType(), claim);
            return claim;
        }

        existing.setClaimCount(claim.getClaimCount());
        existing.setStatusCode(claim.getStatusCode());
        return existing;
    }

    public Map<String, Map<ClaimType, ClaimCountsDto>> getClaimsMap(boolean pended) {
        return pended ? pendedClaimsMap : claimsMap;
    }

    public void addClaimStream(String claimStream) {
        claimStreams.add(claimStream);
    }

    public boolean removeClaimStream(String claimStream) {
        claimsMap.remove(claimStream);
        pendedClaimsMap.remove(claimStream);
        return claimStreams.remove(claimStream);
    }
}
