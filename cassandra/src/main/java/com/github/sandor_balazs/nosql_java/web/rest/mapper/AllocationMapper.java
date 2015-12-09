package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Allocation and its DTO AllocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllocationMapper {

    AllocationDTO allocationToAllocationDTO(Allocation allocation);

    Allocation allocationDTOToAllocation(AllocationDTO allocationDTO);
}
