package com.foodsafety.service;

import com.foodsafety.model.*;
import com.foodsafety.dto.TemporaryExpenseDTO;

import com.foodsafety.mapper.TemporaryExpenseMapper;

import com.foodsafety.repository.BeneficiaryRequestRepository;
import com.foodsafety.repository.CampaignRequestRepository;
import com.foodsafety.repository.TemporaryExpenseRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import com.foodsafety.exception.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TemporaryExpenseService {

	private final TemporaryExpenseMapper temporaryExpenseMapper;
    private final TemporaryExpenseRepository temporaryExpenseRepository;
    private final BeneficiaryRequestRepository beneficiaryRequestRepository;
    private final CampaignRequestRepository campaignRequestRepository;

    public TemporaryExpenseDTO findOne(Long id) {
        log.debug("Request to get temporaryExpense : {}", id);
        TemporaryExpense temporaryExpense = temporaryExpenseRepository.getReferenceById(id);
        TemporaryExpenseDTO temporaryExpenseDTO = temporaryExpenseMapper.fromEntityToDTO(temporaryExpense);

        // Check if BeneficiaryRequest is set before accessing its ID
        BeneficiaryRequest beneficiaryRequest = temporaryExpense.getBeneficiaryRequest();
        if (beneficiaryRequest != null) {
            temporaryExpenseDTO.setId_benfRequest(beneficiaryRequest.getId_benfRequest());
        } else {
            // Set a default value or handle the case where BeneficiaryRequest is null
            temporaryExpenseDTO.setId_benfRequest(null);
        }

        // Check if CampaignRequest is set before accessing its ID
        CampaignRequest campaignRequest = temporaryExpense.getCampaignRequest();
        if (campaignRequest != null) {
            temporaryExpenseDTO.setId_campRequest(campaignRequest.getId_campRequest());
        } else {
            // Set a default value or handle the case where CampaignRequest is null
            temporaryExpenseDTO.setId_campRequest(null);
        }

        temporaryExpenseDTO.setId_donor(temporaryExpense.getId_donor());

        return temporaryExpenseDTO;
    }

    public List<TemporaryExpense> findByCampId(Long id) {
        List<TemporaryExpense> tempExpenses = temporaryExpenseRepository.findByCampID(id);

        if (!tempExpenses.isEmpty()) {
            return tempExpenses;
        } else {
            throw new NoSuchElementException("No temporary expenses found for the given camp ID: " + id);
        }
    }



    public List<TemporaryExpenseDTO> findAll() {
        try {
            List<TemporaryExpenseDTO> temporaryExpenseDTOS = temporaryExpenseRepository.findAllAsDto();
            log.info("temporaryExpense returned successfully");
            return temporaryExpenseDTOS;
        } catch (Exception e) {
            log.error("Cannot get temporaryExpense from source", e);
            return null;
        }
    }

    public List<TemporaryExpenseDTO> findAllByGroupId(String groupId) {
        try {
            List<TemporaryExpense> temporaryExpenseList = temporaryExpenseRepository.findAllByGroupId(groupId);
            List<TemporaryExpenseDTO> temporaryExpenseDTOS = temporaryExpenseMapper.fromEntitiesToDTOList(temporaryExpenseList);

            log.info("TemporaryExpenses gotten successfully");
            return temporaryExpenseDTOS;
        } catch (Exception e) {
            log.error("Cannot get temporaryExpenses from source", e);
            return null;
        }
    }

    public List<TemporaryExpenseDTO> getByDonorID(String id) {
        try {

            List<TemporaryExpense> temporaryExpense = temporaryExpenseRepository.findByDonorID(id);
            List<TemporaryExpenseDTO> temporaryExpenseDTO = temporaryExpenseMapper.fromEntitiesToDTOList(temporaryExpense);

            log.info("temporaryExpense returned successfully");
            return temporaryExpenseDTO;
        } catch (Exception e) {
            log.error("Cannot get temporaryExpense from source", e);
            return null;
        }
    }

    public TemporaryExpenseDTO createTemporaryExpenseBen(TemporaryExpenseDTO temporaryExpenseDTO) {
        try {
            TemporaryExpenseDTO temporaryExpenseDTO1 = new TemporaryExpenseDTO();

            if (temporaryExpenseDTO.getId_benfRequest() != null) {
                BeneficiaryRequest beneficiaryRequest = beneficiaryRequestRepository.findById(temporaryExpenseDTO.getId_benfRequest()).orElse(null);

                if (beneficiaryRequest != null) {
                    // Check if the beneficiaryRequest has an existing temporaryExpense
                    boolean existingTemporaryExpense = temporaryExpenseRepository.existsByBeneficiaryRequest(beneficiaryRequest);

                    if (existingTemporaryExpense) {
                        log.error("A temporaryExpense already exists for the provided BeneficiaryRequest");
                        throw new ExistingTemporaryExpenseException("A temporaryExpense already exists for the provided BeneficiaryRequest");
                    }

                    TemporaryExpense temporaryExpense = new TemporaryExpense();
                    temporaryExpense.setTemporaryExpenseAmount(temporaryExpenseDTO.getTemporaryExpenseAmount());
                   
                    temporaryExpense.setComment(temporaryExpenseDTO.getComment());
                    temporaryExpense.setContributionDate(temporaryExpenseDTO.getContributionDate());
                    temporaryExpense.setGroupId(temporaryExpenseDTO.getGroupId());
                    temporaryExpense.setBeneficiaryRequest(beneficiaryRequest);
                    temporaryExpense.setId_donor(temporaryExpenseDTO.getId_donor());

                    // Set campaignRequest to null
                    temporaryExpense.setCampaignRequest(null);

                    log.debug("Request to save TemporaryExpense: {}", temporaryExpense);
                    temporaryExpense = temporaryExpenseRepository.saveAndFlush(temporaryExpense);
                    log.info("TemporaryExpense added successfully");

                    // Set the relevant values in the return DTO
                    temporaryExpenseDTO1.setTemporaryExpenseId(temporaryExpense.getTemporaryExpenseId());
                    temporaryExpenseDTO1.setTemporaryExpenseAmount(temporaryExpense.getTemporaryExpenseAmount());
                    temporaryExpenseDTO1.setComment(temporaryExpense.getComment());
                    temporaryExpenseDTO1.setContributionDate(temporaryExpense.getContributionDate());
                    temporaryExpenseDTO1.setGroupId(temporaryExpense.getGroupId());
                    temporaryExpenseDTO1.setId_benfRequest(temporaryExpense.getBeneficiaryRequest().getId_benfRequest());
                    temporaryExpenseDTO1.setId_donor(temporaryExpense.getId_donor());

                    return temporaryExpenseDTO1;
                } else {
                    log.error("BeneficiaryRequest not found for the provided ID");
                    throw new BeneficiaryRequestNotFoundException("BeneficiaryRequest not found for the provided ID");
                }
            } else {
                log.error("Invalid beneficiaryRequest ID provided");
                throw new InvalidRequestException("Invalid beneficiaryRequest ID provided");
            }
        } catch (Exception e) {
            log.error("Cannot add TemporaryExpense", e);
            throw new TemporaryExpenseCreationException("Failed to add temporaryExpense", e);
        }
    }

    public TemporaryExpenseDTO createTemporaryExpenseCamp(TemporaryExpenseDTO temporaryExpenseDTO) {
        try {
            TemporaryExpenseDTO temporaryExpenseDTO1 = new TemporaryExpenseDTO();

            if (temporaryExpenseDTO.getId_campRequest() != null) {
                CampaignRequest campaignRequest = campaignRequestRepository.findById(temporaryExpenseDTO.getId_campRequest()).orElse(null);

                if (campaignRequest != null) {

                    TemporaryExpense temporaryExpense = new TemporaryExpense();
                    temporaryExpense.setTemporaryExpenseAmount(temporaryExpenseDTO.getTemporaryExpenseAmount());
                    temporaryExpense.setComment(temporaryExpenseDTO.getComment());
                    temporaryExpense.setContributionDate(temporaryExpenseDTO.getContributionDate());
                    temporaryExpense.setGroupId(temporaryExpenseDTO.getGroupId());
                    temporaryExpense.setCampaignRequest(campaignRequest);
                    temporaryExpense.setId_donor(temporaryExpenseDTO.getId_donor());

                    // Set beneficiaryRequest to null
                    temporaryExpense.setBeneficiaryRequest(null);

                    log.debug("Request to save TemporaryExpense: {}", temporaryExpense);
                    temporaryExpense = temporaryExpenseRepository.saveAndFlush(temporaryExpense);
                    log.info("TemporaryExpense added successfully");

                    // Set the relevant values in the return DTO
                    temporaryExpenseDTO1.setTemporaryExpenseId(temporaryExpense.getTemporaryExpenseId());
                    temporaryExpenseDTO1.setTemporaryExpenseAmount(temporaryExpense.getTemporaryExpenseAmount());
      
                    temporaryExpenseDTO1.setComment(temporaryExpense.getComment());
                    temporaryExpenseDTO1.setContributionDate(temporaryExpense.getContributionDate());
                    temporaryExpenseDTO1.setGroupId(temporaryExpense.getGroupId());
                    temporaryExpenseDTO1.setId_campRequest(temporaryExpense.getCampaignRequest().getId_campRequest());
                    temporaryExpenseDTO1.setId_donor(temporaryExpense.getId_donor());

                    return temporaryExpenseDTO1;
                } else {
                    log.error("CampaignRequest not found for the provided ID");
                    throw new CampaignRequestNotFoundException("CampaignRequest not found for the provided ID");
                }
            } else {
                log.error("Invalid campaignRequest ID provided");
                throw new InvalidRequestException("Invalid campaignRequest ID provided");
            }
        } catch (Exception e) {
            log.error("Cannot add TemporaryExpense", e);
            throw new TemporaryExpenseCreationException("Failed to add temporaryExpense", e);
        }
    }

    public TemporaryExpenseDTO updateTemporaryExpenseBen(TemporaryExpenseDTO temporaryExpenseDTO) {
        try {
            TemporaryExpense temporaryExpense =temporaryExpenseRepository.findById(temporaryExpenseDTO.getTemporaryExpenseId()).orElse(null);

            if (temporaryExpense != null && temporaryExpense.getBeneficiaryRequest() != null) {
                BeneficiaryRequest beneficiaryRequest = temporaryExpense.getBeneficiaryRequest();

                temporaryExpense.setTemporaryExpenseAmount(temporaryExpenseDTO.getTemporaryExpenseAmount());
             
                temporaryExpense.setComment(temporaryExpenseDTO.getComment());
                temporaryExpense.setContributionDate(temporaryExpenseDTO.getContributionDate());
                temporaryExpense.setId_donor(temporaryExpenseDTO.getId_donor());

                // Set campaignRequest to null
                temporaryExpense.setCampaignRequest(null);

                log.debug("Request to update TemporaryExpense: {}", temporaryExpense);
                temporaryExpense = temporaryExpenseRepository.saveAndFlush(temporaryExpense);
                log.info("TemporaryExpense updated successfully");

                // Set the relevant values in the return DTO
                TemporaryExpenseDTO updatedTemporaryExpenseDTO = new TemporaryExpenseDTO();
                updatedTemporaryExpenseDTO.setTemporaryExpenseId(temporaryExpense.getTemporaryExpenseId());
                updatedTemporaryExpenseDTO.setTemporaryExpenseAmount(temporaryExpense.getTemporaryExpenseAmount());
               
                updatedTemporaryExpenseDTO.setComment(temporaryExpense.getComment());
                updatedTemporaryExpenseDTO.setContributionDate(temporaryExpense.getContributionDate());
                updatedTemporaryExpenseDTO.setId_benfRequest(beneficiaryRequest.getId_benfRequest());
                updatedTemporaryExpenseDTO.setId_donor(temporaryExpense.getId_donor());

                return updatedTemporaryExpenseDTO;
            } else {
                log.error("TemporaryExpense or BeneficiaryRequest not found for the provided ID");
                throw new BeneficiaryRequestNotFoundException("TemporaryExpense or BeneficiaryRequest not found for the provided ID");
            }
        } catch (Exception e) {
            log.error("Cannot update TemporaryExpense", e);
            throw new TemporaryExpenseUpdatingException("Failed to add temporaryExpense!",e);
        }
    }

    public TemporaryExpenseDTO updateTemporaryExpenseCamp(TemporaryExpenseDTO temporaryExpenseDTO) {
        try {
            TemporaryExpense temporaryExpense =temporaryExpenseRepository.findById(temporaryExpenseDTO.getTemporaryExpenseId()).orElse(null);

            if (temporaryExpense != null && temporaryExpense.getCampaignRequest() != null) {
                CampaignRequest campaignRequest = temporaryExpense.getCampaignRequest();

                temporaryExpense.setTemporaryExpenseAmount(temporaryExpenseDTO.getTemporaryExpenseAmount());
                temporaryExpense.setComment(temporaryExpenseDTO.getComment());
                temporaryExpense.setContributionDate(temporaryExpenseDTO.getContributionDate());
                temporaryExpense.setId_donor(temporaryExpenseDTO.getId_donor());

                // Set beneficiaryRequest to null
                temporaryExpense.setBeneficiaryRequest(null);

                log.debug("Request to update TemporaryExpense: {}", temporaryExpense);
                temporaryExpense = temporaryExpenseRepository.saveAndFlush(temporaryExpense);
                log.info("TemporaryExpense updated successfully");

                // Set the relevant values in the return DTO
                TemporaryExpenseDTO updatedTemporaryExpenseDTO = new TemporaryExpenseDTO();
                updatedTemporaryExpenseDTO.setTemporaryExpenseId(temporaryExpense.getTemporaryExpenseId());
                updatedTemporaryExpenseDTO.setTemporaryExpenseAmount(temporaryExpense.getTemporaryExpenseAmount());
            
                updatedTemporaryExpenseDTO.setComment(temporaryExpense.getComment());
                updatedTemporaryExpenseDTO.setContributionDate(temporaryExpense.getContributionDate());
                updatedTemporaryExpenseDTO.setId_campRequest(campaignRequest.getId_campRequest());
                updatedTemporaryExpenseDTO.setId_donor(temporaryExpense.getId_donor());

                return updatedTemporaryExpenseDTO;
            } else {
                log.error("TemporaryExpense or CampaignRequest not found for the provided ID");
                throw new CampaignRequestNotFoundException("TemporaryExpense or CampaignRequest not found for the provided ID");
            }
        } catch (Exception e) {
            log.error("Cannot update TemporaryExpense", e);
            throw new TemporaryExpenseUpdatingException("Failed to add temporaryExpense!",e);
        }
    }

    public void removeTemporaryExpense(Long id) {
        temporaryExpenseRepository.deleteById(id);
        log.info("TemporaryExpense removed successfully");

    }


}
