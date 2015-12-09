package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.FundingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Funding and its DTO FundingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FundingMapper {

    FundingDTO fundingToFundingDTO(Funding funding);

    Funding fundingDTOToFunding(FundingDTO fundingDTO);
}
