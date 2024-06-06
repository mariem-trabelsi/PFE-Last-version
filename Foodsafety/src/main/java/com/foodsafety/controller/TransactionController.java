package com.foodsafety.controller;

import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.dto.TransactionDTO;
import com.foodsafety.model.Transaction;
import com.foodsafety.service.TransactionService;
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
@RequestMapping("goods/v1/transaction")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @Autowired
    private MessageSource messageSource;
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        TransactionDTO transactionDTO = transactionService.findOne(id);
        return ResponseEntity.ok().body(transactionDTO);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<TransactionDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(transactionService.findAllByGroupId(groupId));
    }


    @PostMapping("/refund")
    public ResponseEntity<TransactionDTO> createRefundTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createRefundTransaction(transactionDTO);
        return ResponseEntity.ok().body(createdTransaction);
    }
    
    @PostMapping("/withdraw/beneficiary")
    public ResponseEntity<TransactionDTO> createWithdrawBenTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createWithdrawBenTransaction(transactionDTO);
        return ResponseEntity.ok().body(createdTransaction);
    }
    
    @PostMapping("/withdraw/campaign")
    public ResponseEntity<TransactionDTO> createWithdrawCampTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createWithdrawCampTransaction(transactionDTO);
        return ResponseEntity.ok().body(createdTransaction);
    }
    
    //Gotta make it unaccessible or tagged as unused API
    @PutMapping("/refund/{id}")
    public ResponseEntity<TransactionDTO> updateRefundTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(id);
        return ResponseEntity.ok().body(transactionService.updateRefundTransaction(transactionDTO));
    }
    
    //Gotta make it unaccessible or tagged as unused API
    @PutMapping("/withdraw/beneficiary/{id}")
    public ResponseEntity<TransactionDTO> updateWithdrawBenTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(id);
        return ResponseEntity.ok().body(transactionService.updateWithdrawBenTransaction(transactionDTO));
    }
    
  //Gotta make it unaccessible or tagged as unused API
    @PutMapping("/withdraw/campaign/{id}")
    public ResponseEntity<TransactionDTO> updateWithdrawCampTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(id);
        return ResponseEntity.ok().body(transactionService.updateWithdrawCampTransaction(transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTransaction(@PathVariable Long id) {
        transactionService.removeTransaction(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/greeting")
    public String greeting(Locale locale, Model model) {
        String greetingMessage = messageSource.getMessage("greeting", null, locale);
        model.addAttribute("greeting", greetingMessage);
        return "greeting";
    }
    
    @GetMapping("/funds/{fundsId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByFundsId(@PathVariable Long fundsId) {
        List<TransactionDTO> transactions = transactionService.findTransactionsByFundsId(fundsId);
        return ResponseEntity.ok(transactions);
    }
    
   
}
