package com.foodsafety.service;


import com.foodsafety.dto.CampaignRequestDTO;
import com.foodsafety.mapper.TransactionMapper;
import com.foodsafety.mapper.CampaignRequestContainsProductMapper;
import com.foodsafety.mapper.CampaignRequestMapper;
import com.foodsafety.mapper.TemporaryExpenseMapper;
import com.foodsafety.model.*;
import com.foodsafety.repository.CampaignRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional

public class CampaignRequestService {

    private final CampaignRequestRepository campaignRequestRepository;
    private final CampaignRequestMapper campaignRequestMapper;
    private final TemporaryExpenseMapper temporaryExpenseMapper;
    private final CampaignRequestContainsProductMapper campaignRequestContainsProductMapper;
    private final TransactionMapper transactionMapper;


    public CampaignRequestDTO findOne(Long id) {
        log.debug("Request to get funds: {}", id);
        CampaignRequest campaignRequest = campaignRequestRepository.getReferenceById(id);
        CampaignRequestDTO campaignRequestDTO = campaignRequestMapper.fromEntityToDTO(campaignRequest);
        campaignRequestDTO.setTemporaryExpenseDTOS(temporaryExpenseMapper.fromEntitiesToDTOList(campaignRequest.getTemporaryExpenses()));
        campaignRequestDTO.setCampaignRequestContainsProductDTOS(campaignRequestContainsProductMapper.fromEntitiesToDTOList(campaignRequest.getCampaignRequestContainsProducts()));
        campaignRequestDTO.setTransactionDTOS(transactionMapper.fromEntitiesToDTOList(campaignRequest.getTransactions()));
        return campaignRequestDTO;
    }

    public List<CampaignRequestDTO> findAll() {
        try {
            List<CampaignRequest> campaignRequests = campaignRequestRepository.findAll();
            log.info("campaignRequests gotted successfully");
            return campaignRequestMapper.fromEntitiesToDTOList(campaignRequests);

        } catch (Exception e) {
            log.error("Cannot get compaignRequests from source", e);
            return null;
        }
    }
    
    public List<CampaignRequestDTO> findByRequestStatus(String status) {
        List<CampaignRequestDTO> campaignRequestDTOS = campaignRequestMapper.fromEntitiesToDTOList(
                campaignRequestRepository.findByRequestStatus(status));
        return campaignRequestDTOS;
    }

    public List<CampaignRequestDTO> findByRequestStatusAndGroupId(String status, String groupId) {
        List<CampaignRequestDTO> campaignRequestDTOS = campaignRequestMapper.fromEntitiesToDTOList(
                campaignRequestRepository.findByRequestStatusAndGroupId(status,groupId));
        return campaignRequestDTOS;
    }

    public CampaignRequestDTO findByPublicVariable(String publicVariable) {
        CampaignRequest campaignRequest = campaignRequestRepository.findByPublicVariable(publicVariable);
        if (campaignRequest != null) {
            CampaignRequestDTO campaignRequestDTO = campaignRequestMapper.fromEntityToDTO(campaignRequest);
            campaignRequestDTO.setTemporaryExpenseDTOS(temporaryExpenseMapper.fromEntitiesToDTOList(campaignRequest.getTemporaryExpenses()));
            campaignRequestDTO.setTransactionDTOS((transactionMapper.fromEntitiesToDTOList(campaignRequest.getTransactions())));
            campaignRequestDTO.setCampaignRequestContainsProductDTOS(campaignRequestContainsProductMapper.fromEntitiesToDTOList(campaignRequest.getCampaignRequestContainsProducts()));
            return campaignRequestDTO;
        } else {
            return null;
        }
    }

    public List<CampaignRequestDTO> findAllByGroupId(String groupId) {
        try {
            List<CampaignRequest> campaignRequestList = campaignRequestRepository.findAllByGroupId(groupId);
            List<CampaignRequestDTO> campaignRequestDTOS = campaignRequestMapper.fromEntitiesToDTOList(campaignRequestList);

            log.info("campaignRequests gotten successfully");
            return campaignRequestDTOS;
        } catch (Exception e) {
            log.error("Cannot get campaignRequests from source", e);
            return null;
        }
    }

    public CampaignRequestDTO createCampaignRequest(CampaignRequestDTO campaignRequestDTO) {
        try {
            log.debug("Request to save campaignRequest: {}", campaignRequestDTO);
            
            String nomenclatureCmp = genererNomenclature();
            CampaignRequest campaignRequest = campaignRequestMapper.fromDTOToEntity(campaignRequestDTO);
            campaignRequest.setNomenclatureCmp(nomenclatureCmp); 
            campaignRequestRepository.saveAndFlush(campaignRequest);
            
            log.info("CampaignRequest added successfully");
            
            // Convertir CampaignRequest en CampaignRequestDTO
            CampaignRequestDTO campaignRequestDTO1 = campaignRequestMapper.fromEntityToDTO(campaignRequest);
             campaignRequestDTO1.setBeneficiaries(campaignRequest.getBeneficiaries());
             campaignRequestDTO1.setNomenclatureCmp(campaignRequest.getNomenclatureCmp());
             campaignRequestDTO1.setTotalAmount(campaignRequest.getTotalAmount());
             campaignRequestDTO1.setDonateurs(campaignRequest.getDonateurs());
             campaignRequestDTO1.setCollecte(campaignRequest.getCollecte());
             campaignRequestDTO1.setRecuperation(campaignRequest.getRecuperation());
             campaignRequestDTO1.setCompaignEndDate(campaignRequest.getCompaignEndDate());
            // Mapper les listes associées (TemporaryExpenseCamp et CampaignRequestContainsProduct)
            campaignRequestDTO1.setTemporaryExpenseDTOS(temporaryExpenseMapper.fromEntitiesToDTOList(campaignRequest.getTemporaryExpenses()));
            campaignRequestDTO1.setCampaignRequestContainsProductDTOS(campaignRequestContainsProductMapper.fromEntitiesToDTOList(campaignRequest.getCampaignRequestContainsProducts()));
            campaignRequestDTO1.setTransactionDTOS(transactionMapper.fromEntitiesToDTOList(campaignRequest.getTransactions()));
            return campaignRequestDTO1;
        } catch (Exception e) {
            log.error("Cannot add campaignRequest", e);
            return null;
        }
    }

	public String genererNomenclature() {
	    try { 
	        int currentYear = Year.now().getValue();
	        Long lastYear = campaignRequestRepository.findLastYear();
	        Long lastId =   campaignRequestRepository.findLastId();
	        if (lastYear != null && lastYear == currentYear) {
	            lastId++;
	        } else {
	           
	            lastId = 1L;
	        }
	        String formattedId = String.format("%04d", lastId);
	        String nomenclature = "CMP-" + currentYear + "-" + formattedId;
	        return nomenclature;
	    } catch (Exception e) {
	        log.error("Erreur lors de la génération de la nomenclature pour la demande campagne", e);
	        return null;
	    }
	}


    public CampaignRequestDTO updateCampaignRequest(CampaignRequestDTO campaignRequestDTO) {
        try {
            Optional<CampaignRequest> optional = campaignRequestRepository.findById(campaignRequestDTO.getId_campRequest());
            if (optional.isPresent()) {
                CampaignRequest campaignRequest = optional.get();
                campaignRequest.setCampaignName(campaignRequestDTO.getCampaignName());
                campaignRequest.setCampaignStartDate(campaignRequestDTO.getCampaignStartDate());
                campaignRequest.setRequestStatus(campaignRequestDTO.getRequestStatus());
                campaignRequest.setDetailedStatus(campaignRequestDTO.getDetailedStatus());
                campaignRequest.setTotalAmount(campaignRequestDTO.getTotalAmount());
                campaignRequest.setCreationDate(campaignRequestDTO.getCreationDate());
                campaignRequest.setBeneficiaries(campaignRequestDTO.getBeneficiaries());
                campaignRequest.setDonateurs(campaignRequestDTO.getDonateurs());
                campaignRequest.setCollecte(campaignRequestDTO.getCollecte());
                campaignRequest.setRecuperation(campaignRequestDTO.getRecuperation());
                campaignRequest.setCompaignEndDate(campaignRequestDTO.getCompaignEndDate());

                //added in Jul 13, 2023
                campaignRequest.setCampaignRequestNumber(campaignRequestDTO.getCampaignRequestNumber());
                campaignRequest.setComment(campaignRequestDTO.getComment());
                campaignRequest.setPublicVariable(campaignRequestDTO.getPublicVariable());

                campaignRequest.setTransactions(transactionMapper.fromDTOListToEntities(campaignRequestDTO.getTransactionDTOS()));
                campaignRequest.setTemporaryExpenses(temporaryExpenseMapper.fromDTOListToEntities(campaignRequestDTO.getTemporaryExpenseDTOS()));
                campaignRequest.setCampaignRequestContainsProducts(campaignRequestContainsProductMapper.fromDTOListToEntities(campaignRequestDTO.getCampaignRequestContainsProductDTOS()));
                campaignRequestRepository.save(campaignRequest);

                log.info("campaignRequest with id= {} edited successfully", campaignRequest.getId_campRequest());
                return campaignRequestMapper.fromEntityToDTO(campaignRequest);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit compaignRequest ", e);
            return null;
        }
    }

    public void removeCampaignRequest(Long id) {
        campaignRequestRepository.deleteById(id);
        log.info("compaignRequest removed successfully");
    }

    public List<Beneficiary> getBeneficiariesByCampaignId(Long campaignId) {
        Optional<CampaignRequest> optionalCampaignRequest = campaignRequestRepository.findById(campaignId);
        if (optionalCampaignRequest.isPresent()) {
            CampaignRequest campaignRequest = optionalCampaignRequest.get();
            return campaignRequest.getBeneficiaries();
        }
        return Collections.emptyList();
    }
    
    public List<Donor> getDonorsByCampaignId(Long campaignId) {
        Optional<CampaignRequest> optionalCampaignRequest = campaignRequestRepository.findById(campaignId);
        if (optionalCampaignRequest.isPresent()) {
            CampaignRequest campaignRequest = optionalCampaignRequest.get();
            return campaignRequest.getDonateurs();
        }
        return Collections.emptyList();
    }

}
