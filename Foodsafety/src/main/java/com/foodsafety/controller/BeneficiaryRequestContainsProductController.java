package com.foodsafety.controller;

import com.foodsafety.dto.BeneficiaryRequestContainsProductDTO;
import com.foodsafety.service.BeneficiaryRequestContainsProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("goods/v1/beneficiaryRequestContainsProduct")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class BeneficiaryRequestContainsProductController {
    @Autowired
    private MessageSource messageSource;
    private final BeneficiaryRequestContainsProductService beneficiaryRequestContainsProductService;
    
    @Transactional( readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaryRequestContainsProductDTO> getRequestContainsProduct(@PathVariable Long id) {
        BeneficiaryRequestContainsProductDTO containsProductDTO = beneficiaryRequestContainsProductService.findOne(id);
        return ResponseEntity.ok().body(containsProductDTO);
    }

    @Transactional ( readOnly = true)
    @GetMapping
    public ResponseEntity<List<BeneficiaryRequestContainsProductDTO>> findAll() {
        return ResponseEntity.ok(beneficiaryRequestContainsProductService.findAll());
    }
    @GetMapping("/beneficiaryRequest/{id}")
    public ResponseEntity<List<BeneficiaryRequestContainsProductDTO>> getRequestContainsProductByRequestId(@PathVariable Long id) {
        List<BeneficiaryRequestContainsProductDTO> containsProductDTO = beneficiaryRequestContainsProductService.getAllByRequestId(id);
        return ResponseEntity.ok().body(containsProductDTO);
    }

    @PostMapping
    public ResponseEntity<BeneficiaryRequestContainsProductDTO> createRequestContainsProduct(@RequestBody BeneficiaryRequestContainsProductDTO containsProductDTO) {
        return ResponseEntity.ok().body(beneficiaryRequestContainsProductService.createRequestContainsProduct(containsProductDTO));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<BeneficiaryRequestContainsProductDTO> updateRequestContainsProduct(@PathVariable Long id, @RequestBody BeneficiaryRequestContainsProductDTO containsProductDTO) {
        containsProductDTO.setId(id);
        return ResponseEntity.ok().body(beneficiaryRequestContainsProductService.updateRequestContainsProduct(containsProductDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRequestContainsProduct(@PathVariable Long id) {
        beneficiaryRequestContainsProductService.removeRequestContainsProduct(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/greeting")
    public String greeting(Locale locale, Model model) {
        String greetingMessage = messageSource.getMessage("greeting", null, locale);
        model.addAttribute("greeting", greetingMessage);
        return "greeting";
    }

}
