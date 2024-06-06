package com.foodsafety.controller;

import java.util.List;

import com.foodsafety.dto.BeneficiaryRequestDTO;
import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.model.TemporaryExpense;
import com.foodsafety.service.BeneficiaryRequestService;
import com.foodsafety.dto.TemporaryExpenseDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods/v1/beneficiaryRequest")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class BeneficiaryRequestController {

	private  final BeneficiaryRequestService beneficairyRequestService;

	@Transactional( readOnly = true)
	@GetMapping("/{id}")
	public ResponseEntity<BeneficiaryRequestDTO> getBeneficiaryRequest(@PathVariable Long id) {
		BeneficiaryRequestDTO beneficiaryRequestDTO= beneficairyRequestService.findOne(id);
		return ResponseEntity.ok().body(beneficiaryRequestDTO);
	}

	@Transactional(readOnly = true)
	@GetMapping("{id}/temporaryExpense")
	public ResponseEntity<TemporaryExpenseDTO> getTemporaryExpense(@PathVariable Long id) {
		TemporaryExpenseDTO temporaryExpense = beneficairyRequestService.findOne(id).getTemporaryExpenseDTO();
		if (temporaryExpense != null) {
			return ResponseEntity.ok().body(temporaryExpense);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/public/{publicVariable}")
	public ResponseEntity<BeneficiaryRequestDTO> findByPublicVariable(@PathVariable String publicVariable) {
		BeneficiaryRequestDTO beneficiaryRequest = beneficairyRequestService.findByPublicVariable(publicVariable);
		if (beneficiaryRequest != null) {
			return ResponseEntity.ok(beneficiaryRequest);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	
	@GetMapping(value = "/status/{status}")
    public ResponseEntity< List<BeneficiaryRequestDTO>> findByRequestStatus(@PathVariable String status) {
     List <BeneficiaryRequestDTO> beneficiaryRequestDTO =   beneficairyRequestService.findByRequestStatus(status);
        return ResponseEntity.ok(beneficiaryRequestDTO);
    }

	@GetMapping(value = "/status/{status}/groupId/{groupId}")
	public ResponseEntity< List<BeneficiaryRequestDTO>> findByRequestStatusAndGroupId(@PathVariable String status, @PathVariable String groupId) {
		List <BeneficiaryRequestDTO> beneficiaryRequestDTO =   beneficairyRequestService.findByRequestStatusAndGroupId(status,groupId);
		return ResponseEntity.ok(beneficiaryRequestDTO);
	}
	
	@GetMapping(value = "/statuses/{status1}/{status2}")
    public ResponseEntity< List<BeneficiaryRequestDTO>> findByRequestStatuses(@PathVariable String status1, @PathVariable String status2) {
     List <BeneficiaryRequestDTO> beneficiaryRequestDTO =   beneficairyRequestService.findByRequestStatuses(status1, status2);
        return ResponseEntity.ok(beneficiaryRequestDTO);
    }
	
	@Transactional( readOnly = true)
	@GetMapping
	public ResponseEntity<List<BeneficiaryRequestDTO>> findAll() {
		return ResponseEntity.ok(beneficairyRequestService.findAll());
	}

	@Transactional(readOnly = true)
	@GetMapping("/groupId/{groupId}")
	public ResponseEntity<List<BeneficiaryRequestDTO>> findAllByGroupId(@PathVariable String groupId) {
		return ResponseEntity.ok(beneficairyRequestService.findAllByGroupId(groupId));
	}

	@PostMapping
	public ResponseEntity<BeneficiaryRequestDTO> createCompaignRequest (@RequestBody BeneficiaryRequestDTO beneficiaryRequestDTO) {
		return ResponseEntity.ok().body(beneficairyRequestService.createBeneficiaryRequest(beneficiaryRequestDTO));
	}

	@PutMapping( "/{id}")
	public ResponseEntity<BeneficiaryRequestDTO> updateBeneficiaryRequest(@PathVariable Long id, @RequestBody BeneficiaryRequestDTO beneficiaryRequestDTO) {
		beneficiaryRequestDTO.setId_benfRequest(id);
		return ResponseEntity.ok().body(beneficairyRequestService.updateBeneficiaryRequest(beneficiaryRequestDTO));
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeFunds(@PathVariable Long id) {
		beneficairyRequestService.removeBeneficiaryRequest(id);
		return ResponseEntity.ok().build();
	}

}