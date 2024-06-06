package com.foodsafety.controller;


import com.foodsafety.dto.CampaignRequestContainsProductDTO;
import com.foodsafety.service.CampaignRequestContainsProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/campaignRequestContainsProduct")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CampaignRequestContainsProductController {

    private final CampaignRequestContainsProductService campaignRequestContainsProductService;

    @Transactional( readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<CampaignRequestContainsProductDTO> getRequestContainsProduct(@PathVariable Long id) {
        CampaignRequestContainsProductDTO containsProductDTO = campaignRequestContainsProductService.findOne(id);
        return ResponseEntity.ok().body(containsProductDTO);
    }

    @GetMapping("/campaignRequest/{id}")
    public ResponseEntity<List<CampaignRequestContainsProductDTO>> getCampaignRequestContainsProductByRequestId(@PathVariable Long id) {
        List<CampaignRequestContainsProductDTO> containsProductDTO = campaignRequestContainsProductService.getAllByRequestId(id);
        return ResponseEntity.ok().body(containsProductDTO);
    }

    @Transactional ( readOnly = true)
    @GetMapping
    public ResponseEntity<List<CampaignRequestContainsProductDTO>> findAll() {
        return ResponseEntity.ok(campaignRequestContainsProductService.findAll());
    }

    @PostMapping
    public ResponseEntity<CampaignRequestContainsProductDTO> createRequestContainsProduct(@RequestBody CampaignRequestContainsProductDTO containsProductDTO) {
        return ResponseEntity.ok().body(campaignRequestContainsProductService.createRequestContainsProduct(containsProductDTO));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<CampaignRequestContainsProductDTO> updateRequestContainsProduct(@PathVariable Long id, @RequestBody CampaignRequestContainsProductDTO containsProductDTO) {
        containsProductDTO.setId(id);
        return ResponseEntity.ok().body(campaignRequestContainsProductService.updateRequestContainsProduct(containsProductDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRequestContainsProduct(@PathVariable Long id) {
        campaignRequestContainsProductService.removeRequestContainsProduct(id);
        return ResponseEntity.ok().build();
    }
}
