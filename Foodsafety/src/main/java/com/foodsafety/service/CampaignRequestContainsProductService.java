package com.foodsafety.service;

import com.foodsafety.dto.CampaignRequestContainsProductDTO;
import com.foodsafety.mapper.CampaignRequestContainsProductMapper;
import com.foodsafety.model.CampaignRequest;
import com.foodsafety.model.CampaignRequestContainsProduct;
import com.foodsafety.model.Product;

import com.foodsafety.repository.CampaignRequestContainsProductRepository;
import com.foodsafety.repository.CampaignRequestRepository;
import com.foodsafety.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CampaignRequestContainsProductService {

    private final CampaignRequestContainsProductMapper campaignRequestContainsProductMapper;
    private final CampaignRequestContainsProductRepository campaignRequestContainsProductRepository;
    private final ProductRepository productRepository;
    private final CampaignRequestRepository requestRepository;

    public CampaignRequestContainsProductDTO findOne(Long id) {
        CampaignRequestContainsProduct campaignRequestContainsProduct = campaignRequestContainsProductRepository.getReferenceById(id);

        CampaignRequestContainsProductDTO containsProductDTO = campaignRequestContainsProductMapper.fromEntityToDTO(campaignRequestContainsProduct);
        containsProductDTO.setId_product(campaignRequestContainsProduct.getProduct().getProductId());
        containsProductDTO.setId_campRequest(campaignRequestContainsProduct.getCampaignRequest().getId_campRequest());
        containsProductDTO.setId(campaignRequestContainsProduct.getCampaignRequest().getId_campRequest());

        return containsProductDTO;

    }

    public List<CampaignRequestContainsProductDTO> findAll() {
        try {
            List<CampaignRequestContainsProduct> containsProductList = campaignRequestContainsProductRepository.findAll();
            List<CampaignRequestContainsProductDTO> containsProductDTOS = campaignRequestContainsProductMapper.fromEntitiesToDTOList(containsProductList);
            return containsProductDTOS;
        } catch (Exception e) {
            log.error("Cannot get request with products from source", e);
            return null;
        }
    }

    public CampaignRequestContainsProductDTO createRequestContainsProduct(CampaignRequestContainsProductDTO containsProductDTO) {


        try {
            Product product = productRepository.findById(containsProductDTO.getId_product()).get();
            CampaignRequest request = requestRepository.findById(containsProductDTO.getId_campRequest()).get();

            CampaignRequestContainsProduct campaignRequestContainsProduct = campaignRequestContainsProductMapper.fromDTOToEntity(containsProductDTO);
            campaignRequestContainsProduct.setProduct(product);

            campaignRequestContainsProduct.setCampaignRequest(request);
            campaignRequestContainsProduct = campaignRequestContainsProductRepository.saveAndFlush(campaignRequestContainsProduct);
            log.info("containsProduct added successfully ");
            CampaignRequestContainsProductDTO containsProductDTO1 = campaignRequestContainsProductMapper.fromEntityToDTO(campaignRequestContainsProduct);
            containsProductDTO1.setId_product(product.getProductId());
            containsProductDTO1.setId_campRequest(request.getId_campRequest());
            return containsProductDTO1;

        } catch (Exception e) {
            log.error("Cannot add requestContainsProduct ", e);
            return null;
        }
    }

    public CampaignRequestContainsProductDTO updateRequestContainsProduct(CampaignRequestContainsProductDTO containsProductDTO) {
        try {
            Product product = productRepository.findById(containsProductDTO.getId_product()).get();
            CampaignRequest request = requestRepository.findById(containsProductDTO.getId_campRequest()).get();

            Optional<CampaignRequestContainsProduct> optional = campaignRequestContainsProductRepository.findById(containsProductDTO.getId());
            if (optional.isPresent()) {
                CampaignRequestContainsProduct campaignRequestContainsProduct = optional.get();
                campaignRequestContainsProduct.setQuantity(containsProductDTO.getQuantity());
                campaignRequestContainsProduct.setComment(containsProductDTO.getComment());
                campaignRequestContainsProduct.setSize(containsProductDTO.getSize());
                campaignRequestContainsProduct.setProduct(product);
                campaignRequestContainsProduct.setCampaignRequest(request);
                campaignRequestContainsProductRepository.save(campaignRequestContainsProduct);

                log.info("beneficiaryRequestContainsProduct with id= {} edited successfully", campaignRequestContainsProduct.getId());
                CampaignRequestContainsProductDTO containsProductDTO1 = campaignRequestContainsProductMapper.fromEntityToDTO(campaignRequestContainsProduct);
                containsProductDTO1.setId_product(product.getProductId());
                containsProductDTO1.setId_campRequest(request.getId_campRequest());
                return containsProductDTO1;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit requestContainsProduct ", e);
            return null;
        }
    }

    public void removeRequestContainsProduct(Long id) {
        campaignRequestContainsProductRepository.deleteById(id);
        log.info("requestContainsProduct removed successfully");

    }

    public List<CampaignRequestContainsProductDTO> getAllByRequestId (Long id) {
        List<CampaignRequestContainsProductDTO> containsProductDTOS = campaignRequestContainsProductRepository.findByCampaignRequestId(id);
        return containsProductDTOS;
    }
}
//List<CampaignRequestContainsProductDTO> containsProductDTOS = campaignRequestContainsProductMapper.fromEntitiesToDTOList(
//campaignRequestContainsProductRepository.findByCampaignRequestId(id));
//return containsProductDTOS;

