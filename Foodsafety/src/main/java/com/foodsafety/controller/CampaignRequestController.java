package com.foodsafety.controller;

import java.util.ArrayList;
import java.util.List;

import com.foodsafety.dto.CampaignRequestDTO;
import com.foodsafety.dto.TemporaryExpenseDTO;
import com.foodsafety.model.Beneficiary;
import com.foodsafety.model.Donor;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.foodsafety.service.CampaignRequestService;
@RestController
@RequestMapping("goods/v1/campaignRequest")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class 	CampaignRequestController {

	private  final CampaignRequestService campaignRequestService;

	@Transactional( readOnly = true)
	@GetMapping("/{id}")
	public ResponseEntity<CampaignRequestDTO> getCampaignRequest(@PathVariable Long id) {
		CampaignRequestDTO campaignRequestDTO = campaignRequestService.findOne(id);
		return ResponseEntity.ok().body(campaignRequestDTO);
	}

	
	@Transactional( readOnly = true)
	@GetMapping
	public ResponseEntity<List<CampaignRequestDTO>> findAll() {
		return ResponseEntity.ok(campaignRequestService.findAll());
	}
	
	@GetMapping(value = "/status/{status}")
    public ResponseEntity<List<CampaignRequestDTO>> findByRequestStatus(@PathVariable String status) {
     List <CampaignRequestDTO> campaignRequestDTO =   campaignRequestService.findByRequestStatus(status);
        return ResponseEntity.ok(campaignRequestDTO);
    }

	@GetMapping(value = "/status/{status}/groupId/{groupId}")
	public ResponseEntity< List<CampaignRequestDTO>> findByRequestStatusAndGroupId(@PathVariable String status, @PathVariable String groupId) {
		List <CampaignRequestDTO> campaignRequestDTO =   campaignRequestService.findByRequestStatusAndGroupId(status,groupId);
		return ResponseEntity.ok(campaignRequestDTO);
	}

	@GetMapping("/public/{publicVariable}")
	public ResponseEntity<CampaignRequestDTO> findByPublicVariable(@PathVariable String publicVariable) {
		CampaignRequestDTO campaignRequest = campaignRequestService.findByPublicVariable(publicVariable);
		if (campaignRequest != null) {
			return ResponseEntity.ok(campaignRequest);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Transactional(readOnly = true)
	@GetMapping("/groupId/{groupId}")
	public ResponseEntity<List<CampaignRequestDTO>> findAllByGroupId(@PathVariable String groupId) {
		return ResponseEntity.ok(campaignRequestService.findAllByGroupId(groupId));
	}

	@PostMapping
	public ResponseEntity<CampaignRequestDTO> creatCampaignRequest(@RequestBody CampaignRequestDTO campaignRequestDTO) {
		return ResponseEntity.ok().body(campaignRequestService.createCampaignRequest(campaignRequestDTO));
	}

	@PutMapping( "/{id}")
	public ResponseEntity<CampaignRequestDTO> updateCampaignRequest (@PathVariable Long id, @RequestBody CampaignRequestDTO campaignRequestDTO) {
		campaignRequestDTO.setId_campRequest(id);
		return ResponseEntity.ok().body(campaignRequestService.updateCampaignRequest(campaignRequestDTO));
	}




	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeCampaignRequest(@PathVariable Long id) {
		campaignRequestService.removeCampaignRequest(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{campaignId}/beneficiaries")
	public ResponseEntity<List<Beneficiary>> getBeneficiariesByCampaignId(@PathVariable Long campaignId) {
	    List<Beneficiary> beneficiaries = campaignRequestService.getBeneficiariesByCampaignId(campaignId);
	    
	    if (beneficiaries.isEmpty()) {
	        return ResponseEntity.ok(new ArrayList<Beneficiary>()); // Renvoie une liste vide
	    } else {
	        return ResponseEntity.ok(beneficiaries);
	    }
	}

	 
	 @GetMapping("/{campaignId}/donors")
	    public ResponseEntity<List<Donor>> getDonorsByCampaignId(@PathVariable Long campaignId) {
	        List<Donor> donors = campaignRequestService.getDonorsByCampaignId(campaignId);
	        if (donors.isEmpty()) {
	            return ResponseEntity.ok(new ArrayList<Donor>());
	        } else {
	            return ResponseEntity.ok(donors);
	        }
	    }
	@Transactional(readOnly = true)
	@GetMapping("{id}/temporaryExpenses")
	public ResponseEntity<List<TemporaryExpenseDTO>> getTemporaryExpenses(@PathVariable Long id) {
		List<TemporaryExpenseDTO> temporaryExpenses = campaignRequestService.findOne(id).getTemporaryExpenseDTOS();
		if (!temporaryExpenses.isEmpty()) {
			return ResponseEntity.ok().body(temporaryExpenses);
		} else {
			return ResponseEntity.ok().body(null);
		}
	}
}
