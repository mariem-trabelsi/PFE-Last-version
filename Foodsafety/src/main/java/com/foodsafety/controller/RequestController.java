package com.foodsafety.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/*
import java.util.List;

@RestController
@RequestMapping("goods/v1/request")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @Transactional( readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        RequestDTO campaignRequestDTO = requestService.findOne(id);
        return ResponseEntity.ok().body(campaignRequestDTO);
    }
    @Transactional( readOnly = true)
    @GetMapping
    public ResponseEntity<List<RequestDTO>> findAll() {
        return ResponseEntity.ok(requestService.findAll());
    }

    @PostMapping
    public ResponseEntity<RequestDTO> creatRequest(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok().body(requestService.createRequest(requestDTO));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<RequestDTO> updateRequest (@PathVariable Long id, @RequestBody RequestDTO requestDTO) {
        requestDTO.setRequestId(id);
        return ResponseEntity.ok().body(requestService.updateRequest(requestDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRequest(@PathVariable Long id) {
        requestService.removeRequest(id);
        return ResponseEntity.ok().build();
    }
}
*/