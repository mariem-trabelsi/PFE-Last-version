package com.foodsafety.controller;

import com.foodsafety.dto.TemporaryExpenseDTO;
import com.foodsafety.model.TemporaryExpense;
import com.foodsafety.service.TemporaryExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/temporaryExpense")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TemporaryExpenseController {

    private final TemporaryExpenseService temporaryExpenseService;

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<TemporaryExpenseDTO> getTemporaryExpense(@PathVariable Long id) {
        TemporaryExpenseDTO temporaryExpenseDTO = temporaryExpenseService.findOne(id);
        return ResponseEntity.ok().body(temporaryExpenseDTO);
    }
    
//    getAllTemporaryExpensesBen, getAllTemporaryExpenseCamp

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<TemporaryExpenseDTO>> findAll() {
        return ResponseEntity.ok(temporaryExpenseService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<TemporaryExpenseDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(temporaryExpenseService.findAllByGroupId(groupId));
    }

    @Transactional(readOnly = true)
    @GetMapping("/donor/{id}")
    public ResponseEntity<List<TemporaryExpenseDTO>> getByDonorID(@PathVariable String id) {

        List<TemporaryExpenseDTO> TemporaryDTOs = temporaryExpenseService.getByDonorID(id);
        return ResponseEntity.ok(TemporaryDTOs);
    }

    @Transactional(readOnly = true)
    @GetMapping("/camp/{id}")
    public ResponseEntity<List<TemporaryExpense>> findByCampID(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<TemporaryExpense> temporaryExpenseDTOs = temporaryExpenseService.findByCampId(id);

        if (!temporaryExpenseDTOs.isEmpty()) {
            return ResponseEntity.ok(temporaryExpenseDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/beneficiary")
    public ResponseEntity<TemporaryExpenseDTO> createTemporaryExpenseBen(@RequestBody TemporaryExpenseDTO temporaryExpenseDTO) {
        TemporaryExpenseDTO createdTemporaryExpense = temporaryExpenseService.createTemporaryExpenseBen(temporaryExpenseDTO);
        return ResponseEntity.ok().body(createdTemporaryExpense);
    }

    @PostMapping("/campaign")
    public ResponseEntity<TemporaryExpenseDTO> createTemporaryExpenseCamp(@RequestBody TemporaryExpenseDTO temporaryExpenseDTO) {
        TemporaryExpenseDTO createdTemporaryExpense = temporaryExpenseService.createTemporaryExpenseCamp(temporaryExpenseDTO);
        return ResponseEntity.ok().body(createdTemporaryExpense);
    }

    @PutMapping("/beneficiary/{id}")
    public ResponseEntity<TemporaryExpenseDTO> updateTemporaryExpenseBen(@PathVariable Long id, @RequestBody TemporaryExpenseDTO temporaryExpenseDTO) {
        temporaryExpenseDTO.setTemporaryExpenseId(id);
        return ResponseEntity.ok().body(temporaryExpenseService.updateTemporaryExpenseBen(temporaryExpenseDTO));
    }

    @PutMapping("/campaign/{id}")
    public ResponseEntity<TemporaryExpenseDTO> updateTemporaryExpenseCamp(@PathVariable Long id, @RequestBody TemporaryExpenseDTO temporaryExpenseDTO) {
        temporaryExpenseDTO.setTemporaryExpenseId(id);
        return ResponseEntity.ok().body(temporaryExpenseService.updateTemporaryExpenseCamp(temporaryExpenseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTemporaryExpense(@PathVariable Long id) {
        temporaryExpenseService.removeTemporaryExpense(id);
        return ResponseEntity.ok().build();
    }


}
