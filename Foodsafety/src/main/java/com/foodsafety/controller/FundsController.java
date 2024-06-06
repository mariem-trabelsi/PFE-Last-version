package com.foodsafety.controller;


import com.foodsafety.dto.FundsDTO;

import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.model.Funds;
import com.foodsafety.service.FundsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/funds")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class FundsController {

    private final FundsService fundsService;

    @Transactional( readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<FundsDTO> getFunds(@PathVariable Long id) {
        FundsDTO fundsDTO = fundsService.findOne(id);
        return ResponseEntity.ok().body(fundsDTO);
    }

    @Transactional( readOnly = true)
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<FundsDTO> getFundsByDonorId(@PathVariable String donorId) {
        try {
            FundsDTO funds = fundsService.getFundsByDonorId(donorId);
            return ResponseEntity.ok(funds);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    

    @Transactional ( readOnly = true)
    @GetMapping
    public ResponseEntity<List<FundsDTO>> findAll() {
        return ResponseEntity.ok(fundsService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<FundsDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(fundsService.findAllByGroupId(groupId));
    }


    @PostMapping
    public ResponseEntity<FundsDTO> createFunds(@RequestBody FundsDTO fundsDTO) {
        return ResponseEntity.ok().body(fundsService.createFunds(fundsDTO));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<FundsDTO> updateFunds(@PathVariable Long id, @RequestBody FundsDTO fundsDTO) {
        fundsDTO.setFundsId(id);
        return ResponseEntity.ok().body(fundsService.updateFunds(fundsDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFunds(@PathVariable Long id) {
        fundsService.removeFunds(id);
        return ResponseEntity.ok().build();
    }


}
