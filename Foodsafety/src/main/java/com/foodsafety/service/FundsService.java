package com.foodsafety.service;

import com.foodsafety.dto.FundsDTO;
import com.foodsafety.dto.TransactionDTO;
import com.foodsafety.mapper.FundsMapper;
import com.foodsafety.mapper.TransactionMapper;
import com.foodsafety.model.Funds;
import com.foodsafety.model.Transaction;
import com.foodsafety.repository.FundsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class FundsService {

    private final FundsRepository fundsRepository;
    private final FundsMapper fundsMapper;
    private final TransactionMapper transactionMapper;

    public FundsDTO findOne(Long id) {
        log.debug("Request to get funds: {}", id);
        Funds funds = fundsRepository.getReferenceById(id);
        FundsDTO fundsDTO =  fundsMapper.fromEntityToDTO(funds);
        fundsDTO.setTransactionDTOS(transactionMapper.fromEntitiesToDTOList(funds.getTransactions()));
        return fundsDTO;
    }

    public List<FundsDTO> findAll() {
        try {
            List<FundsDTO> fundsDTOS = fundsRepository.findAllAsDto();
            log.info("funds gotted successfully");
            return fundsDTOS;
        } catch (Exception e) {
            log.error("Cannot get funds from source", e);
            return null;
        }
    }

    public List<FundsDTO> findAllByGroupId(String groupId) {
        try {
            List<Funds> fundsList = fundsRepository.findAllByGroupId(groupId);
            List<FundsDTO> fundsDTOS = fundsMapper.fromEntitiesToDTOList(fundsList);

            log.info("Funds gotten successfully");
            return fundsDTOS;
        } catch (Exception e) {
            log.error("Cannot get funds from source", e);
            return null;
        }
    }


    public FundsDTO getFundsByDonorId(String donorId) {
        Funds funds = fundsRepository.findByDonorId(donorId);
        if (funds != null) {
            return fundsMapper.fromEntityToDTO(funds);
        } else {
            throw new IllegalArgumentException("Invalid donorId");
        }
    }

    public FundsDTO createFunds(FundsDTO fundsDTO) {
        try {
            log.debug("Request to save funds: {}", fundsDTO);
            Funds funds = fundsMapper.fromDTOToEntity(fundsDTO);
            funds = fundsRepository.saveAndFlush(funds);

            log.info("funds added successfully  ");
            FundsDTO fundsDTO1 = fundsMapper.fromEntityToDTO(funds);
            fundsDTO1.setTransactionDTOS(transactionMapper.fromEntitiesToDTOList(funds.getTransactions()));

            return fundsDTO1;
        } catch (Exception e) {
            log.error("Cannot add funds ", e);
            return null;
        }
    }
    public FundsDTO updateFunds(FundsDTO fundsDTO) {
        try {
            Optional<Funds> optional = fundsRepository.findById(fundsDTO.getFundsId());
            if (optional.isPresent()) {
                Funds funds = optional.get();
                funds.setDonorId(fundsDTO.getDonorId());
                funds.setCurrentAmount(fundsDTO.getCurrentAmount());
                funds.setTransactions(transactionMapper.fromDTOListToEntities(fundsDTO.getTransactionDTOS()));
                funds.setVersDate(fundsDTO.getVersDate());

                fundsRepository.save(funds);
                log.info("funds with id= {} edited successfully", funds.getFundsId());
                return fundsMapper.fromEntityToDTO(funds);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit funds ", e);
            return null;
        }
    }

    public void removeFunds(Long id) {
        fundsRepository.deleteById(id);
        log.info("funds removed successfully");
    }


}
