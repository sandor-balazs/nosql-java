package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.FundingService;
import com.github.sandor_balazs.nosql_java.domain.Funding;
import com.github.sandor_balazs.nosql_java.repository.FundingRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.FundingDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.FundingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Funding.
 */
@Service
public class FundingServiceImpl implements FundingService{

    private final Logger log = LoggerFactory.getLogger(FundingServiceImpl.class);
    
    @Inject
    private FundingRepository fundingRepository;
    
    @Inject
    private FundingMapper fundingMapper;
    
    /**
     * Save a funding.
     * @return the persisted entity
     */
    public FundingDTO save(FundingDTO fundingDTO) {
        log.debug("Request to save Funding : {}", fundingDTO);
        Funding funding = fundingMapper.fundingDTOToFunding(fundingDTO);
        funding = fundingRepository.save(funding);
        FundingDTO result = fundingMapper.fundingToFundingDTO(funding);
        return result;
    }

    /**
     *  get all the fundings.
     *  @return the list of entities
     */
    public List<FundingDTO> findAll() {
        log.debug("Request to get all Fundings");
        List<FundingDTO> result = fundingRepository.findAll().stream()
            .map(fundingMapper::fundingToFundingDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one funding by id.
     *  @return the entity
     */
    public FundingDTO findOne(String id) {
        log.debug("Request to get Funding : {}", id);
        Funding funding = fundingRepository.findOne(UUID.fromString(id));
        FundingDTO fundingDTO = fundingMapper.fundingToFundingDTO(funding);
        return fundingDTO;
    }

    /**
     *  delete the  funding by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Funding : {}", id);
        fundingRepository.delete(UUID.fromString(id));
    }
}
