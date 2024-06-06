package com.foodsafety.service;


import com.foodsafety.dto.BeneficiaryRequestContainsProductDTO;
import com.foodsafety.dto.BeneficiaryRequestDTO;
import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.mapper.TemporaryExpenseMapper;
import com.foodsafety.mapper.BeneficiaryRequestMapper;
import com.foodsafety.mapper.BeneficiaryRequestContainsProductMapper;
import com.foodsafety.model.BeneficiaryRequest;
import com.foodsafety.model.Category;
import com.foodsafety.model.TemporaryExpense;
import com.foodsafety.repository.BeneficiaryRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class BeneficiaryRequestService {

	private final BeneficiaryRequestRepository beneficiaryRequestRepository;
	private final BeneficiaryRequestMapper beneficiaryRequestMapper;
	private final BeneficiaryRequestContainsProductMapper beneficiaryRequestContainsProductMapper;
	private final TemporaryExpenseMapper temporaryExpenseMapper;


	public BeneficiaryRequestDTO findOne(Long id) {
		log.debug("Request to get beneficiaryRequest: {}", id);
		BeneficiaryRequest beneficiaryRequest = beneficiaryRequestRepository.getReferenceById(id);
		BeneficiaryRequestDTO beneficiaryRequestDTO = beneficiaryRequestMapper.fromEntityToDTO(beneficiaryRequest);
		beneficiaryRequestDTO.setTemporaryExpenseDTO(temporaryExpenseMapper.fromEntityToDTO(beneficiaryRequest.getTemporaryExpense()));
		beneficiaryRequestDTO.setBeneficiaryRequestContainsProductDTOS((List<BeneficiaryRequestContainsProductDTO>) beneficiaryRequestContainsProductMapper.fromEntitiesToDTOList(beneficiaryRequest.getBeneficiaryRequestContainsProducts()));

		return beneficiaryRequestDTO;
	}

	public BeneficiaryRequestDTO findByPublicVariable(String publicVariable) {
		BeneficiaryRequest beneficiaryRequest = beneficiaryRequestRepository.findByPublicVariable(publicVariable);
		if (beneficiaryRequest != null) {
			BeneficiaryRequestDTO beneficiaryRequestDTO = beneficiaryRequestMapper.fromEntityToDTO(beneficiaryRequest);
			beneficiaryRequestDTO.setTemporaryExpenseDTO(temporaryExpenseMapper.fromEntityToDTO(beneficiaryRequest.getTemporaryExpense()));
			beneficiaryRequestDTO.setBeneficiaryRequestContainsProductDTOS(beneficiaryRequestContainsProductMapper.fromEntitiesToDTOList(beneficiaryRequest.getBeneficiaryRequestContainsProducts()));
			return beneficiaryRequestDTO;
		} else {
			return null;
		}
	}

	public TemporaryExpense getTemporaryExpense(Long id) {
		BeneficiaryRequestDTO beneficiaryRequestDTO = this.findOne(id);
		BeneficiaryRequest beneficiaryRequest = beneficiaryRequestMapper.fromDTOToEntity(beneficiaryRequestDTO);
		TemporaryExpense temporaryExpense = beneficiaryRequest.getTemporaryExpense();
		return temporaryExpense;
	}

	public List<BeneficiaryRequestDTO> findAll() {
		try {
			List<BeneficiaryRequest> beneficiaryRequests = beneficiaryRequestRepository.findAll();
			log.info("beneficiaryRequests gotten successfully");
			return beneficiaryRequestMapper.fromEntitiesToDTOList(beneficiaryRequests);

		} catch (Exception e) {
			log.error("Cannot get beneficiaryRequests from source", e);
			return null;
		}
	}
	
	public List<BeneficiaryRequestDTO> findByRequestStatus(String status) {
        List<BeneficiaryRequestDTO> beneficiaryRequestDTOS = beneficiaryRequestMapper.fromEntitiesToDTOList(
                beneficiaryRequestRepository.findByRequestStatus(status));
        return beneficiaryRequestDTOS;
    }

	public List<BeneficiaryRequestDTO> findByRequestStatusAndGroupId(String status, String groupId) {
		List<BeneficiaryRequestDTO> beneficiaryRequestDTOS = beneficiaryRequestMapper.fromEntitiesToDTOList(
				beneficiaryRequestRepository.findByRequestStatusAndGroupId(status,groupId));
		return beneficiaryRequestDTOS;
	}
	
	public List<BeneficiaryRequestDTO> findByRequestStatuses(String status1, String status2) {
        List<BeneficiaryRequestDTO> beneficiaryRequestDTOS = beneficiaryRequestMapper.fromEntitiesToDTOList(
                beneficiaryRequestRepository.findByRequestStatus(status1));
        beneficiaryRequestDTOS.addAll(beneficiaryRequestMapper.fromEntitiesToDTOList(
                beneficiaryRequestRepository.findByRequestStatus(status2)));
        return beneficiaryRequestDTOS;
    }

	public List<BeneficiaryRequestDTO> findAllByGroupId(String groupId) {
		try {
			List<BeneficiaryRequest> beneficiaryRequestList = beneficiaryRequestRepository.findAllByGroupId(groupId);
			List<BeneficiaryRequestDTO> beneficiaryRequestDTOS = beneficiaryRequestMapper.fromEntitiesToDTOList(beneficiaryRequestList);

			log.info("beneficiaryRequests gotten successfully");
			return beneficiaryRequestDTOS;
		} catch (Exception e) {
			log.error("Cannot get beneficiaryRequests from source", e);
			return null;
		}
	}

	
	public BeneficiaryRequestDTO createBeneficiaryRequest(BeneficiaryRequestDTO beneficiaryRequestDTO) {
		try {
			 String nomenclature = genererNomenclature();
			log.debug("Request to save BeneficiaryRequest: {}", beneficiaryRequestDTO);
			BeneficiaryRequest beneficiaryRequest = beneficiaryRequestMapper.fromDTOToEntity(beneficiaryRequestDTO);
			beneficiaryRequest.setNomenclature(nomenclature);
			beneficiaryRequestRepository.saveAndFlush(beneficiaryRequest);

			log.info("BeneficiaryRequest added successfully  ");
			BeneficiaryRequestDTO beneficiaryRequestDTO1 = beneficiaryRequestMapper.fromEntityToDTO(beneficiaryRequest);
			beneficiaryRequestDTO1.setTemporaryExpenseDTO(temporaryExpenseMapper.fromEntityToDTO(beneficiaryRequest.getTemporaryExpense()));
			beneficiaryRequestDTO1.setBeneficiaryRequestContainsProductDTOS(beneficiaryRequestContainsProductMapper.fromEntitiesToDTOList(beneficiaryRequest.getBeneficiaryRequestContainsProducts()));

			return beneficiaryRequestDTO1;
		} catch (Exception e) {
			log.error("Cannot add beneficiaryRequest ", e);
			return null;
		}
	}
	
	public String genererNomenclature() {
	    try { 
	        int currentYear = Year.now().getValue();
	        Long lastYear = beneficiaryRequestRepository.findLastYear();
	        Long lastId = beneficiaryRequestRepository.findLastId();
	        if (lastYear != null && lastYear == currentYear) {
	            lastId++;
	        } else {
	           
	            lastId = 1L;
	        }
	        String formattedId = String.format("%04d", lastId);
	        String nomenclature = "DEM-" + currentYear + "-" + formattedId;
	        return nomenclature;
	    } catch (Exception e) {
	        log.error("Erreur lors de la génération de la nomenclature pour la demande", e);
	        return null;
	    }
	}



	public BeneficiaryRequestDTO updateBeneficiaryRequest(BeneficiaryRequestDTO beneficiaryRequestDTO) {
		try {
			Optional<BeneficiaryRequest> optional = beneficiaryRequestRepository.findById(beneficiaryRequestDTO.getId_benfRequest());
			if (optional.isPresent()) {
				BeneficiaryRequest beneficiaryRequest = optional.get();
				beneficiaryRequest.setPublicVariable(beneficiaryRequestDTO.getPublicVariable());
				beneficiaryRequest.setBeneficiaryRequestNumber(beneficiaryRequestDTO.getBeneficiaryRequestNumber());
				beneficiaryRequest.setId_beneficiary(beneficiaryRequestDTO.getId_beneficiary());
				beneficiaryRequest.setRequestStatus(beneficiaryRequestDTO.getRequestStatus());
				beneficiaryRequest.setDetailedStatus(beneficiaryRequestDTO.getDetailedStatus());
				beneficiaryRequest.setTotalAmount(beneficiaryRequestDTO.getTotalAmount());
				beneficiaryRequest.setCreationDate(beneficiaryRequestDTO.getCreationDate());
				beneficiaryRequest.setId_donor(beneficiaryRequestDTO.getId_donor());
				beneficiaryRequest.setComment(beneficiaryRequestDTO.getComment());
				beneficiaryRequest.setTemporaryExpense(temporaryExpenseMapper.fromDTOToEntity(beneficiaryRequestDTO.getTemporaryExpenseDTO()));
				beneficiaryRequest.setBeneficiaryRequestContainsProducts(beneficiaryRequestContainsProductMapper.fromDTOListToEntities(beneficiaryRequestDTO.getBeneficiaryRequestContainsProductDTOS()));
				beneficiaryRequestRepository.save(beneficiaryRequest);

				log.info("beneficiaryRequest with id= {} edited successfully", beneficiaryRequest.getId_benfRequest());
				return beneficiaryRequestMapper.fromEntityToDTO(beneficiaryRequest);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Cannot edit beneficiaryRequest ", e);
			return null;
		}
	}

	public void removeBeneficiaryRequest(Long id) {
		beneficiaryRequestRepository.deleteById(id);
		log.info("beneficiaryRequest removed successfully");
	}

}