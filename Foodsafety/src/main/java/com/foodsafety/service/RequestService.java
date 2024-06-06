package com.foodsafety.service;

/*
import com.foodsafety.dto.RequestDTO;
import com.foodsafety.mapper.RequestMapper;
import com.foodsafety.mapper.TemporaryExpenseMapper;
import com.foodsafety.model.Request;
import com.foodsafety.repository.RequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private RequestMapper requestMapper;
    private final TemporaryExpenseMapper temporaryExpenseMapper;

    public RequestDTO findOne(Long id) {
        log.debug("Request to get Request: {}", id);
        Request request = requestRepository.getReferenceById(id);
        RequestDTO requestDTO = requestMapper.fromEntityToDTO(request);
        requestDTO.setTemporaryExpenseBenDTOS(temporaryExpenseMapper.fromEntitiesToDTOList(request.getTemporaryExpensBens()));

        return requestDTO;
    }

    public List<RequestDTO> findAll() {
        try {
            List<RequestDTO> requestDTOS = requestRepository.findAllAsDto();

            log.info("Requests gotted successfully");
            return requestDTOS;
        } catch (Exception e) {
            log.error("Cannot get Requests from source", e);
            return null;
        }

    }

    public RequestDTO createRequest(RequestDTO requestDTO) {
        try {
            log.debug("Request to save Request : {}", requestDTO);
            Request request = requestMapper.fromDTOToEntity(requestDTO);
            request = requestRepository.saveAndFlush(request);
            log.info("Request added successfully  ");
            RequestDTO requestDTO1 = requestMapper.fromEntityToDTO(request);
            requestDTO1.setTemporaryExpenseBenDTOS(temporaryExpenseMapper.fromEntitiesToDTOList(request.getTemporaryExpensBens()));

            return requestDTO1;
        } catch (Exception e) {
            log.error("Cannot add Request ", e);
            return null;
        }
    }

    public RequestDTO updateRequest(RequestDTO requestDTO) {
        try {
            Optional<Request> optional = requestRepository.findById(requestDTO.getRequestId());
            if (optional.isPresent()) {
                Request request = optional.get();
                request.setRequestStatus(requestDTO.getRequestStatus());
                request.setCreationDate(requestDTO.getCreationDate());
                request.setTotalAmount(requestDTO.getTotalAmount());
                request.setTemporaryExpensBens(temporaryExpenseMapper.fromDTOListToEntities(requestDTO.getTemporaryExpenseBenDTOS()));
                requestRepository.save(request);
                log.info("request with id= {} edited successfully", request.getRequestId());
                return requestMapper.fromEntityToDTO(request);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit request ", e);
            return null;
        }
    }

    public void removeRequest(Long id) {
        requestRepository.deleteById(id);
        log.info("request removed successfully");
    }


}
*/