package com.mypoc.ClaimApplication.repository;

import com.mypoc.ClaimApplication.dto.ClaimCounts;
import com.mypoc.ClaimApplication.dto.ClaimCountsDto;
import com.mypoc.ClaimApplication.enums.ClaimType;
import com.mypoc.ClaimApplication.utils.EcmServicesConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Repository
public class ClaimRepositoryCustomImpl implements ClaimRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean getClaimCountsByClaimStatus(ClaimCounts claimCounts) {
        log.info(" Executing SQL query to fetch claim counts...");

        String sql = """
    SELECT 
        CLAIM_TYPE,
        STATUS_CD,
        COALESCE(CLAIM_STREAM, ' ') AS CLAIM_STREAM,
        COUNT(MPI_CLAIM_ID) AS CLAIM_COUNT
    FROM CLAIM
    WHERE STATUS_CD = :statusCd
    GROUP BY CLAIM_TYPE, STATUS_CD, CLAIM_STREAM
    ORDER BY CLAIM_STREAM ASC
""";


        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("statusCd", EcmServicesConstants.CLAIM_STATUS_MANUAL_MATCH);

        List<Object[]> results = query.getResultList();
        boolean containsBlankStream = false;

        for (Object[] obj : results) {
            ClaimCountsDto dto = new ClaimCountsDto();

            dto.setClaimType(ClaimType.valueOf(obj[0].toString())); // enum safe
            dto.setStatusCode(Long.parseLong(obj[1].toString()));
            dto.setClaimStream(obj[2] != null ? obj[2].toString() : "");
            dto.setClaimCount(((Number) obj[3]).intValue());

            if (StringUtils.isBlank(dto.getClaimStream())) {
                dto.setClaimStream(StringUtils.EMPTY);
                containsBlankStream = true;
            }

            claimCounts.updateClaimCount(dto, false);
        }

        log.info("Query execution completed. {} records processed.", results.size());
        return containsBlankStream;
    }
}
