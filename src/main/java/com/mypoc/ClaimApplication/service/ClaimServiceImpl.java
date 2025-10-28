package com.mypoc.ClaimApplication.service;

import com.mypoc.ClaimApplication.dto.ClaimCountsDto;
import com.mypoc.ClaimApplication.dto.CountClaimResponse;
import com.mypoc.ClaimApplication.enums.ClaimType;
import com.mypoc.ClaimApplication.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    @Override
    public CountClaimResponse getAllClaimCounts() {
        List<Object[]> results = claimRepository.getAllClaimCounts();

        List<ClaimCountsDto> dtoList = results.stream()
                .map(obj -> {
                    ClaimCountsDto dto = new ClaimCountsDto();

                    //  claimStream (safe String conversion)
                    dto.setClaimStream(obj[0] == null ? "" : String.valueOf(obj[0]));

                    //  claimType (convert String → Enum safely)
                    if (obj[1] != null) {
                        try {
                            dto.setClaimType(ClaimType.valueOf(obj[1].toString().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            // If DB value doesn’t match enum constant, skip or set as null
                            dto.setClaimType(null);
                        }
                    } else {
                        dto.setClaimType(null);
                    }

                    //  claimCount (safe Number → int)
                    if (obj[2] instanceof Number) {
                        dto.setClaimCount(((Number) obj[2]).intValue());
                    } else {
                        dto.setClaimCount(0);
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        CountClaimResponse response = new CountClaimResponse();
        response.setStatus("SUCCESS");
        response.setMessage("Fetched " + dtoList.size() + " records successfully");
        response.setClaimCounts(dtoList);

        return response;
    }
}
