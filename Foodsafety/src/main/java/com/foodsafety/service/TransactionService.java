package com.foodsafety.service;

import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.dto.TransactionDTO;
import com.foodsafety.mapper.TransactionMapper;
import com.foodsafety.model.*;
import com.foodsafety.repository.BeneficiaryRequestRepository;
import com.foodsafety.repository.CampaignRequestRepository;
import com.foodsafety.repository.FundsRepository;
import com.foodsafety.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.foodsafety.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TransactionService {
 
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final FundsRepository fundsRepository;
    private final BeneficiaryRequestRepository beneficiaryRequestRepository;
    private final CampaignRequestRepository campaignRequestRepository;

    public TransactionDTO findOne(Long id) {
        log.debug("Request to get transaction: {}", id);
        Transaction transaction = transactionRepository.getReferenceById(id);
        TransactionDTO transactionDTO = transactionMapper.fromEntityToDTO(transaction);

        // Check if BeneficiaryRequest is set before accessing its ID
        BeneficiaryRequest beneficiaryRequest = transaction.getBeneficiaryRequest();
        if (beneficiaryRequest != null) {
            transactionDTO.setId_benfRequest(beneficiaryRequest.getId_benfRequest());
        } else {
            // Set a default value or handle the case where BeneficiaryRequest is null
            transactionDTO.setId_benfRequest(null);
        }

        // Check if CampaignRequest is set before accessing its ID
        CampaignRequest campaignRequest = transaction.getCampaignRequest();
        if (campaignRequest != null) {
            transactionDTO.setId_campRequest(campaignRequest.getId_campRequest());
        } else {
            // Set a default value or handle the case where CampaignRequest is null
            transactionDTO.setId_campRequest(null);
        }

        // Since id_funds is not null, no need for a null check
        transactionDTO.setId_funds(transaction.getFunds().getFundsId());

        return transactionDTO;
    }


    public List<TransactionDTO> findAll() {
        try {
            List<TransactionDTO> transactionDTOS = transactionRepository.findAllAsDto();
            log.info("Transactions gotted successfully");
            return transactionDTOS;
        } catch (Exception e) {
            log.error("Cannot get Transactions from source", e);
            return null;
        }
    }

    public List<TransactionDTO> findAllByGroupId(String groupId) {
        try {
            List<Transaction> transactionList = transactionRepository.findAllByGroupId(groupId);
            List<TransactionDTO> transactionDTOS = transactionMapper.fromEntitiesToDTOList(transactionList);

            log.info("Transactions gotten successfully");
            return transactionDTOS;
        } catch (Exception e) {
            log.error("Cannot get transactions from source", e);
            return null;
        }
    }

    public TransactionDTO createRefundTransaction(TransactionDTO transactionDTO) {
        try {
            TransactionDTO transactionDTO1 = new TransactionDTO();

            if (transactionDTO.getId_funds() != null) {
                Funds funds = fundsRepository.findById(transactionDTO.getId_funds()).orElse(null);
                if (funds != null) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                    transaction.setTransactionType(true);
                    transaction.setTransactionDate(transactionDTO.getTransactionDate());
                    transaction.setComment(transactionDTO.getComment());
                    transaction.setGroupId(transactionDTO.getGroupId());
                    transaction.setFunds(funds);

                    // Set other related entities to null
                    transaction.setBeneficiaryRequest(null);
                    transaction.setCampaignRequest(null);

                    log.debug("Request to save Transaction: {}", transaction);
                    transaction = transactionRepository.saveAndFlush(transaction);
                    log.info("Transaction added successfully");

                    // Set the relevant values in the return DTO
                    transactionDTO1.setTransactionId(transaction.getTransactionId());
                    transactionDTO1.setTransactionAmount(transaction.getTransactionAmount());
                    transactionDTO1.setTransactionType(transaction.getTransactionType());
                    transactionDTO1.setTransactionDate(transaction.getTransactionDate());
                    transactionDTO1.setComment(transaction.getComment());
                    transactionDTO1.setGroupId(transaction.getGroupId());
                    transactionDTO1.setId_funds(transaction.getFunds().getFundsId());

                    return transactionDTO1;
                } else {
                    log.error("Funds not found for the provided ID");
                    throw new FundsNotFoundException("Funds not found for the provided ID"); // throw an exception indicating funds not found
                }
            } else {
                log.error("Invalid funds ID provided");
                throw new InvalidFundsIdException("Invalid funds ID provided"); // throw an exception indicating invalid funds ID
            }

        } catch (Exception e) {
            log.error("Cannot add Transaction", e);
            throw new TransactionCreationException("Failed to add transaction", e); // throw an exception indicating failure to add transaction
        }
    }

    public TransactionDTO createWithdrawBenTransaction(TransactionDTO transactionDTO) {
        try {
            TransactionDTO transactionDTO1 = new TransactionDTO();

            if (transactionDTO.getId_funds() != null && transactionDTO.getId_benfRequest() != null) {
                Funds funds = fundsRepository.findById(transactionDTO.getId_funds()).orElse(null);
                BeneficiaryRequest beneficiaryRequest = beneficiaryRequestRepository.findById(transactionDTO.getId_benfRequest()).orElse(null);

                if (funds != null) {
                    if (beneficiaryRequest != null) {
                        // Check if the beneficiaryRequest has an existing transaction
                        boolean existingTransaction = transactionRepository.existsByBeneficiaryRequest(beneficiaryRequest);

                        if (existingTransaction) {
                            log.error("A transaction already exists for the provided BeneficiaryRequest");
                            throw new ExistingTransactionException("A transaction already exists for the provided BeneficiaryRequest");
                        }

                        Transaction transaction = new Transaction();
                        transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                        transaction.setTransactionType(false);
                        transaction.setTransactionDate(transactionDTO.getTransactionDate());
                        transaction.setComment(transactionDTO.getComment());
                        transaction.setGroupId(transactionDTO.getGroupId());
                        transaction.setFunds(funds);
                        transaction.setBeneficiaryRequest(beneficiaryRequest);

                        // Set other related entities to null
                        transaction.setCampaignRequest(null);

                        log.debug("Request to save Transaction: {}", transaction);
                        transaction = transactionRepository.saveAndFlush(transaction);
                        log.info("Transaction added successfully");

                        // Set the relevant values in the return DTO
                        transactionDTO1.setTransactionId(transaction.getTransactionId());
                        transactionDTO1.setTransactionAmount(transaction.getTransactionAmount());
                        transactionDTO1.setTransactionType(transaction.getTransactionType());
                        transactionDTO1.setTransactionDate(transaction.getTransactionDate());
                        transactionDTO1.setComment(transaction.getComment());
                        transactionDTO1.setGroupId(transaction.getGroupId());
                        transactionDTO1.setId_funds(transaction.getFunds().getFundsId());
                        transactionDTO1.setId_benfRequest(transaction.getBeneficiaryRequest().getId_benfRequest());

                        return transactionDTO1;
                    } else {
                        log.error("BeneficiaryRequest not found for the provided ID");
                        throw new BeneficiaryRequestNotFoundException("BeneficiaryRequest not found for the provided ID");
                    }
                } else {
                    log.error("Funds not found for the provided ID");
                    throw new FundsNotFoundException("Funds not found for the provided ID");
                }
            } else {
                log.error("Invalid funds ID or beneficiaryRequest ID provided");
                throw new InvalidRequestException("Invalid funds ID or beneficiaryRequest ID provided");
            }
        } catch (Exception e) {
            log.error("Cannot add Transaction", e);
            throw new TransactionCreationException("Failed to add transaction", e); 
        }
    }

    public TransactionDTO createWithdrawCampTransaction(TransactionDTO transactionDTO) {
        try {
            TransactionDTO transactionDTO1 = new TransactionDTO();

            if (transactionDTO.getId_funds() != null && transactionDTO.getId_campRequest() != null) {
                Funds funds = fundsRepository.findById(transactionDTO.getId_funds()).orElse(null);
                CampaignRequest campaignRequest = campaignRequestRepository.findById(transactionDTO.getId_campRequest()).orElse(null);

                if (funds != null) {
                    if (campaignRequest != null) {

                        Transaction transaction = new Transaction();
                        transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                        transaction.setTransactionType(false);
                        transaction.setTransactionDate(transactionDTO.getTransactionDate());
                        transaction.setComment(transactionDTO.getComment());
                        transaction.setGroupId(transactionDTO.getGroupId());
                        transaction.setFunds(funds);
                        transaction.setCampaignRequest(campaignRequest);

                        // Set other related entities to null
                        transaction.setBeneficiaryRequest(null);

                        log.debug("Request to save Transaction: {}", transaction);
                        transaction = transactionRepository.saveAndFlush(transaction);
                        log.info("Transaction added successfully");

                        // Set the relevant values in the return DTO
                        transactionDTO1.setTransactionId(transaction.getTransactionId());
                        transactionDTO1.setTransactionAmount(transaction.getTransactionAmount());
                        transactionDTO1.setTransactionType(transaction.getTransactionType());
                        transactionDTO1.setTransactionDate(transaction.getTransactionDate());
                        transactionDTO1.setComment(transaction.getComment());
                        transactionDTO1.setGroupId(transaction.getGroupId());
                        transactionDTO1.setId_funds(transaction.getFunds().getFundsId());
                        transactionDTO1.setId_campRequest(transaction.getCampaignRequest().getId_campRequest());

                        return transactionDTO1;
                    } else {
                        log.error("CampaignRequest not found for the provided ID");
                        throw new CampaignRequestNotFoundException("CampaignRequest not found for the provided ID");
                    }
                } else {
                    log.error("Funds not found for the provided ID");
                    throw new FundsNotFoundException("Funds not found for the provided ID");
                }
            } else {
                log.error("Invalid funds ID or campaignRequest ID provided");
                throw new InvalidRequestException("Invalid funds ID or campaignRequest ID provided");
            }
        } catch (Exception e) {
            log.error("Cannot add Transaction", e);
            throw new TransactionCreationException("Failed to add transaction", e);
        }
    }

    
    public TransactionDTO updateRefundTransaction(TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionRepository.findById(transactionDTO.getTransactionId()).orElse(null);

            if (transaction != null && transaction.getFunds() != null) {
                Funds funds = transaction.getFunds();
                transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                transaction.setTransactionType(true);
                transaction.setTransactionDate(transactionDTO.getTransactionDate());
                transaction.setComment(transactionDTO.getComment());

                // Set other related entities to null
                transaction.setBeneficiaryRequest(null);
                transaction.setCampaignRequest(null);

                log.debug("Request to update Transaction: {}", transaction);
                transaction = transactionRepository.saveAndFlush(transaction);
                log.info("Transaction updated successfully");

                // Set the relevant values in the return DTO
                TransactionDTO updatedTransactionDTO = new TransactionDTO();
                updatedTransactionDTO.setTransactionId(transaction.getTransactionId());
                updatedTransactionDTO.setTransactionAmount(transaction.getTransactionAmount());
                updatedTransactionDTO.setTransactionType(transaction.getTransactionType());
                updatedTransactionDTO.setTransactionDate(transaction.getTransactionDate());
                updatedTransactionDTO.setComment(transaction.getComment());
                updatedTransactionDTO.setId_funds(funds.getFundsId());

                return updatedTransactionDTO;
            } else {
                log.info("Transaction or Funds not found for the provided ID");
                return null; // or throw an exception indicating transaction or funds not found
            }
        } catch (Exception e) {
            log.error("Cannot update Transaction", e);
            return null; // or throw an exception indicating failure to update transaction
        }
    }

    public TransactionDTO updateWithdrawBenTransaction(TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionRepository.findById(transactionDTO.getTransactionId()).orElse(null);

            if (transaction != null && transaction.getFunds() != null && transaction.getBeneficiaryRequest() != null) {
                Funds funds = transaction.getFunds();
                BeneficiaryRequest beneficiaryRequest = transaction.getBeneficiaryRequest();

                transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                transaction.setTransactionType(false);
                transaction.setTransactionDate(transactionDTO.getTransactionDate());
                transaction.setComment(transactionDTO.getComment());

                // Set other related entities to null
                transaction.setCampaignRequest(null);

                log.debug("Request to update Transaction: {}", transaction);
                transaction = transactionRepository.saveAndFlush(transaction);
                log.info("Transaction updated successfully");

                // Set the relevant values in the return DTO
                TransactionDTO updatedTransactionDTO = new TransactionDTO();
                updatedTransactionDTO.setTransactionId(transaction.getTransactionId());
                updatedTransactionDTO.setTransactionAmount(transaction.getTransactionAmount());
                updatedTransactionDTO.setTransactionType(transaction.getTransactionType());
                updatedTransactionDTO.setTransactionDate(transaction.getTransactionDate());
                updatedTransactionDTO.setComment(transaction.getComment());
                updatedTransactionDTO.setId_funds(funds.getFundsId());
                updatedTransactionDTO.setId_benfRequest(beneficiaryRequest.getId_benfRequest());

                return updatedTransactionDTO;
            } else {
                log.info("Transaction, Funds, or BeneficiaryRequest not found for the provided ID");
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot update Transaction", e);
            return null; // or throw an exception indicating failure to update transaction
        }
    }

    public TransactionDTO updateWithdrawCampTransaction(TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionRepository.findById(transactionDTO.getTransactionId()).orElse(null);

            if (transaction != null && transaction.getFunds() != null && transaction.getCampaignRequest() != null) {
                Funds funds = transaction.getFunds();
                CampaignRequest campaignRequest = transaction.getCampaignRequest();

                transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
                transaction.setTransactionType(false);
                transaction.setTransactionDate(transactionDTO.getTransactionDate());
                transaction.setComment(transactionDTO.getComment());

                // Set other related entities to null
                transaction.setBeneficiaryRequest(null);

                log.debug("Request to update Transaction: {}", transaction);
                transaction = transactionRepository.saveAndFlush(transaction);
                log.info("Transaction updated successfully");

                // Set the relevant values in the return DTO
                TransactionDTO updatedTransactionDTO = new TransactionDTO();
                updatedTransactionDTO.setTransactionId(transaction.getTransactionId());
                updatedTransactionDTO.setTransactionAmount(transaction.getTransactionAmount());
                updatedTransactionDTO.setTransactionType(transaction.getTransactionType());
                updatedTransactionDTO.setTransactionDate(transaction.getTransactionDate());
                updatedTransactionDTO.setComment(transaction.getComment());
                updatedTransactionDTO.setId_funds(funds.getFundsId());
                updatedTransactionDTO.setId_campRequest(campaignRequest.getId_campRequest());

                return updatedTransactionDTO;
            } else {
                log.info("Transaction, Funds, or CampaignRequest not found for the provided ID");
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot update Transaction", e);
            return null; // or throw an exception indicating failure to update transaction
        }
    }


    public void removeTransaction(Long id) {
        transactionRepository.deleteById(id);
        log.info("Transaction removed successfully");
    }
    
    public List<TransactionDTO> findTransactionsByFundsId(Long fundsId) {
        Funds funds = fundsRepository.findById(fundsId)
                .orElseThrow(() -> new FundsNotFoundException("Funds not found for the provided ID: " + fundsId));
        
        List<TransactionDTO> transactionsByFunds = transactionMapper.fromEntitiesToDTOList(
                transactionRepository.findAllByFunds(funds));

        transactionsByFunds.forEach(transactionDTO -> {
            Transaction transaction = transactionRepository.getReferenceById(transactionDTO.getTransactionId());
            
            BeneficiaryRequest beneficiaryRequest = transaction.getBeneficiaryRequest();
            Long benfRequestId = (beneficiaryRequest != null) ? beneficiaryRequest.getId_benfRequest() : null;
            transactionDTO.setId_benfRequest(benfRequestId);
            
            CampaignRequest campaignRequest = transaction.getCampaignRequest();
            Long campRequestId = (campaignRequest != null) ? campaignRequest.getId_campRequest() : null;
            transactionDTO.setId_campRequest(campRequestId);
        });

        log.info("Transactions by funds retrieved successfully");
        return transactionsByFunds;
    }

 

}