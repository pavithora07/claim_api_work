package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.ClaimCounts;
import com.mypoc.ClaimApplication.dto.ClaimCountsDto;
import com.mypoc.ClaimApplication.enums.ClaimStream;
import com.mypoc.ClaimApplication.enums.ClaimType;
import com.mypoc.ClaimApplication.projection.ClaimCountProjection;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import com.mypoc.ClaimApplication.utils.EcmServicesConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * ClaimServiceImpl
 *
 * - Fetches claim counts grouped by claim stream and type from DB.
 * - Handles both pended and normal claims in a unified response model.
 * - Ensures backward compatibility with legacy ClaimCounts JSON.
 */
@Slf4j
@Service("claimService")
@Transactional(readOnly = true)
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    /**
     * Retrieves aggregated claim counts for claims requiring manual match.
     *
     * @return a populated {@link ClaimCounts} structure suitable for JSON response.
     */
    @Override
    public ClaimCounts getClaimCountsByClaimStatus() {

        log.info("▶ Starting claim count retrieval for status: {}", EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH);

        // Initialize known claim streams (from enum + blank)
        LinkedHashSet<String> claimStreams = new LinkedHashSet<>();
        claimStreams.add(StringUtils.EMPTY); // blank entry to handle null stream cases
        for (ClaimStream cs : ClaimStream.values()) {
            claimStreams.add(cs.toString());
        }

        // Prepare ClaimCounts data structure
        ClaimCounts claimCounts = new ClaimCounts(
                claimStreams,
                EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH,
                "Claims Requiring Manual Match",
                true
        );

        log.debug("Initialized ClaimCounts with streams: {}", claimStreams);

        // Query DB for aggregated claim counts
        List<ClaimCountProjection> rows = claimRepository.getClaimCountsByStatus(EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH);
        log.info("Fetched {} record(s) from database for status {}", rows.size(), EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH);

        boolean containsBlankClaimStream = false;

        // Map DB rows into DTOs and update ClaimCounts
        for (ClaimCountProjection row : rows) {
            try {
                String rawClaimType = row.getClaimType();
                String claimTypeChar = (rawClaimType != null && rawClaimType.length() > 0)
                        ? rawClaimType.substring(0, 1)
                        : rawClaimType;

                // Convert claim type safely
                ClaimType claimType;
                try {
                    claimType = ClaimType.valueOf(claimTypeChar);
                } catch (IllegalArgumentException iae) {
                    log.warn("Unknown claim type '{}' in DB row — skipping entry.", claimTypeChar);
                    continue;
                }

                String claimStream = StringUtils.defaultIfBlank(row.getClaimStream(), StringUtils.EMPTY);
                if (StringUtils.isBlank(claimStream)) {
                    containsBlankClaimStream = true;
                }

                Integer claimCount = row.getClaimCount() != null ? row.getClaimCount() : 0;
                String isPendedVal = StringUtils.defaultIfBlank(row.getIsPended(), "N");

                // Build ClaimCountsDto object
                ClaimCountsDto dto = new ClaimCountsDto();
                dto.setClaimType(claimType);
                dto.setClaimStream(claimStream);
                dto.setClaimCount(claimCount);
                dto.setMpiClaimId(row.getStatusCd()); // map to status for traceability

                // Dynamic message generation
                String messagePrefix = "Y".equalsIgnoreCase(isPendedVal) ? "Pended " : "";
                String claimForm = (claimType == ClaimType.H ? "HCFA " : "UB ");
                dto.setMessage(messagePrefix + claimForm + "Claims Requiring Manual Match");

                //  Update ClaimCounts structure
                claimCounts.updateClaimCount(dto, "Y".equalsIgnoreCase(isPendedVal));

            } catch (Exception e) {
                log.error("❌ Error processing claim row: {}", e.getMessage(), e);
            }
        }

        //  Cleanup redundant claim streams
        if (!containsBlankClaimStream) {
            claimCounts.removeClaimStream(StringUtils.EMPTY);
            log.debug("Removed blank claimStream entry from final output");
        }

        // Optional cleanup: remove legacy/unwanted stream if applicable
        claimCounts.removeClaimStream("SAVILITY");

        log.info("Claim count retrieval completed. Returning {} streams.", claimCounts.getClaimStreams().size());
        return claimCounts;
    }
}
