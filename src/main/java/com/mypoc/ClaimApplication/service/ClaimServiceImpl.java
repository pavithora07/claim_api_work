package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.entity.Claim;
import com.mypoc.ClaimApplication.entity.ClaimCountDetail;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link ClaimService} responsible for all
 * business logic related to claim operations.
 *
 * <p>Delegates database access to {@link ClaimRepository}
 * and provides higher-level aggregation, validation, and response mapping.
 *
 * <p>Key responsibilities:
 * <ul>
 *   <li>Count claims by type and/or status</li>
 *   <li>Fetch claims by claim type</li>
 *   <li>Generate grouped claim count summaries</li>
 * </ul>
 *
 * @author
 * @since 2025-10
 */
@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    // ------------------------------------------------------------------------
    // 1️⃣ Count claims based on filters
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * <p>Behavior:
     * <ul>
     *   <li>If both claimType and statusCd provided → counts matching records.</li>
     *   <li>If only claimType provided → counts all claims of that type.</li>
     * </ul>
     */
    @Override
    public CountClaimResponse countClaims(String claimType, String statusCd) {
        log.debug("[ClaimService] Counting claims | claimType={} | statusCd={}", claimType, statusCd);

        long claimCount = (statusCd != null && !statusCd.isEmpty())
                ? claimRepository.countByClaimTypeAndStatusCd(claimType, statusCd)
                : claimRepository.countByClaimType(claimType);

        log.info("[ClaimService] Claim count computed | claimType={} | statusCd={} | totalCount={}",
                claimType, statusCd, claimCount);

        CountClaimResponse response = new CountClaimResponse();
        response.setClaimType(claimType);
        response.setStatusCd(statusCd);
        response.setClaimCount(claimCount);
        response.setStatus("SUCCESS");

        return response;
    }

    // ------------------------------------------------------------------------
    // 2️⃣ Retrieve all claims for a given type
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Claim> getClaimsByType(String claimType) {
        log.debug("[ClaimService] Fetching claims for claimType={}", claimType);

        List<Claim> claims = claimRepository.findByClaimType(claimType);

        if (claims.isEmpty()) {
            log.warn("[ClaimService] No claims found for claimType={}", claimType);
        } else {
            log.info("[ClaimService] Fetched {} claim(s) for claimType={}", claims.size(), claimType);
        }

        return claims;
    }

    // ------------------------------------------------------------------------
    // 3️⃣ Retrieve grouped claim counts
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClaimCountDetail> getAllClaimCounts() {
        log.info("[ClaimService] Fetching grouped claim count summary...");

        List<Object[]> rawResults = claimRepository.findGroupedClaimCounts();
        List<ClaimCountDetail> summaryList = new ArrayList<>();

        if (rawResults == null || rawResults.isEmpty()) {
            log.warn("[ClaimService] No grouped claim data found in repository.");
            return summaryList;
        }

        for (Object[] record : rawResults) {
            ClaimCountDetail dto = mapToClaimCountDetail(record);
            summaryList.add(dto);

            log.debug("[ClaimService] Processed record | type={} | status={} | stream={} | count={}",
                    dto.getClaimType(), dto.getStatusCd(), dto.getClaimStream(), dto.getClaimCount());
        }

        log.info("[ClaimService] Grouped claim summary retrieval complete | totalRecords={}", summaryList.size());
        return summaryList;
    }

    // ------------------------------------------------------------------------
    // 🔧 Helper Methods
    // ------------------------------------------------------------------------

    /**
     * Converts raw query result (Object[]) into {@link ClaimCountDetail} safely.
     *
     * @param record raw database row
     * @return mapped ClaimCountDetail DTO
     */
    private ClaimCountDetail mapToClaimCountDetail(Object[] record) {
        if (record == null || record.length < 4) {
            log.warn("[ClaimService] Skipping invalid record (expected 4+ columns).");
            return new ClaimCountDetail();
        }

        String claimType   = Objects.toString(record[0], "").trim();
        String statusCd    = Objects.toString(record[1], "").trim();
        String claimStream = Objects.toString(record[2], "").trim();
        Long claimCount    = record[3] instanceof Number ? ((Number) record[3]).longValue() : 0L;

        ClaimCountDetail dto = new ClaimCountDetail();
        dto.setClaimType(claimType);
        dto.setStatusCd(statusCd);
        dto.setClaimStream(claimStream);
        dto.setClaimCount(claimCount);
        dto.setMiscText(buildMiscText(claimType, "N")); // default pendedFlag = N

        return dto;
    }

    /**
     * Builds a descriptive message for the claim summary.
     *
     * @param claimType  the type of claim
     * @param pendedFlag indicates if claim is pended ("Y"/"N")
     * @return formatted descriptive text
     */
    private String buildMiscText(String claimType, String pendedFlag) {
        boolean isPended = "Y".equalsIgnoreCase(pendedFlag);
        return (isPended ? "Pended " : "") + claimType + " Claims Requiring Manual Match";
    }
}
