package com.foodsafety.service;


import com.foodsafety.dto.BeneficiaryRequestContainsProductDTO;
import com.foodsafety.mapper.BeneficiaryRequestContainsProductMapper;
import com.foodsafety.model.BeneficiaryRequest;
import com.foodsafety.model.BeneficiaryRequestContainsProduct;
import com.foodsafety.model.Product;
import com.foodsafety.repository.BeneficiaryRequestRepository;
import com.foodsafety.repository.ProductRepository;
import com.foodsafety.repository.BeneficiaryRequestContainsProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BeneficiaryRequestContainsProductService {

    private final BeneficiaryRequestContainsProductMapper beneficiaryRequestContainsProductMapper;
    private final BeneficiaryRequestContainsProductRepository beneficiaryRequestContainsProductRepository;
    private final ProductRepository productRepository;
    private final BeneficiaryRequestRepository requestRepository;

    public BeneficiaryRequestContainsProductDTO findOne(Long id) {
        BeneficiaryRequestContainsProduct beneficiaryRequestContainsProduct = beneficiaryRequestContainsProductRepository.getReferenceById(id);

        BeneficiaryRequestContainsProductDTO containsProductDTO = beneficiaryRequestContainsProductMapper.fromEntityToDTO(beneficiaryRequestContainsProduct);
        containsProductDTO.setId_product(beneficiaryRequestContainsProduct.getProduct().getProductId());
        containsProductDTO.setId_benfRequest(beneficiaryRequestContainsProduct.getBeneficiaryRequest().getId_benfRequest());
        containsProductDTO.setId(beneficiaryRequestContainsProduct.getId());

        return containsProductDTO;

    }

    public List<BeneficiaryRequestContainsProductDTO> findAll() {
        try {
            List<BeneficiaryRequestContainsProductDTO> containsProductDTOS = beneficiaryRequestContainsProductRepository.findAllAsDto();
            return containsProductDTOS;
        } catch (Exception e) {
            log.error("Cannot get request with products from source", e);
            return null;
        }
    }

    public BeneficiaryRequestContainsProductDTO createRequestContainsProduct(BeneficiaryRequestContainsProductDTO containsProductDTO) {


        try {
            Product product = productRepository.findById(containsProductDTO.getId_product()).get();
            BeneficiaryRequest request = requestRepository.findById(containsProductDTO.getId_benfRequest()).get();
            log.debug("Request to save containsProductDTO: {}", containsProductDTO);
            BeneficiaryRequestContainsProduct beneficiaryRequestContainsProduct = beneficiaryRequestContainsProductMapper.fromDTOToEntity(containsProductDTO);
            beneficiaryRequestContainsProduct.setProduct(product);
            beneficiaryRequestContainsProduct.setBeneficiaryRequest(request);
            beneficiaryRequestContainsProduct = beneficiaryRequestContainsProductRepository.saveAndFlush(beneficiaryRequestContainsProduct);
            log.info("containsProduct added successfully ");
            BeneficiaryRequestContainsProductDTO containsProductDTO1 = beneficiaryRequestContainsProductMapper.fromEntityToDTO(beneficiaryRequestContainsProduct);
            containsProductDTO1.setId_product(product.getProductId());
            containsProductDTO1.setId_benfRequest(request.getId_benfRequest());
            return containsProductDTO1;

        } catch (Exception e) {
            log.error("Cannot add requestContainsProduct ", e);
            return null;
        }
    }

    public BeneficiaryRequestContainsProductDTO updateRequestContainsProduct(BeneficiaryRequestContainsProductDTO beneficiaryRequestContainsProductDTO) {
        try {
            Product product = productRepository.findById(beneficiaryRequestContainsProductDTO.getId_product()).get();
            BeneficiaryRequest request = requestRepository.findById(beneficiaryRequestContainsProductDTO.getId_benfRequest()).get();

            Optional<BeneficiaryRequestContainsProduct> optional = beneficiaryRequestContainsProductRepository.findById(beneficiaryRequestContainsProductDTO.getId());
            if (optional.isPresent()) {
                BeneficiaryRequestContainsProduct beneficiaryRequestContainsProduct = optional.get();
                beneficiaryRequestContainsProduct.setQuantity(beneficiaryRequestContainsProductDTO.getQuantity());
                beneficiaryRequestContainsProduct.setComment(beneficiaryRequestContainsProductDTO.getComment());
                beneficiaryRequestContainsProduct.setSize(beneficiaryRequestContainsProductDTO.getSize());
                beneficiaryRequestContainsProduct.setProduct(product);
                beneficiaryRequestContainsProduct.setBeneficiaryRequest(request);
                beneficiaryRequestContainsProductRepository.save(beneficiaryRequestContainsProduct);

                log.info("beneficiaryRequestContainsProduct with id= {} edited successfully", beneficiaryRequestContainsProduct.getId());
                BeneficiaryRequestContainsProductDTO containsProductDTO = beneficiaryRequestContainsProductMapper.fromEntityToDTO(beneficiaryRequestContainsProduct);
                containsProductDTO.setId_product(product.getProductId());
                containsProductDTO.setId_benfRequest(request.getId_benfRequest());
                return containsProductDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit requestContainsProduct ", e);
            return null;
        }
    }

    public void removeRequestContainsProduct(Long id) {
        beneficiaryRequestContainsProductRepository.deleteById(id);
        log.info("requestContainsProduct removed successfully");

    }

    public List<BeneficiaryRequestContainsProductDTO> getAllByRequestId (Long id) {
        List<BeneficiaryRequestContainsProductDTO> containsProductDTOS = beneficiaryRequestContainsProductRepository.findByBeneficiaryRequestIdBenfRequest(id);
        return containsProductDTOS;
    }
    
}
