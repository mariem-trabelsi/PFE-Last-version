package com.foodsafety.service;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.ProductDTO;
import com.foodsafety.mapper.BeneficiaryRequestContainsProductMapper;
import com.foodsafety.mapper.ProductMapper;
import com.foodsafety.mapper.SubCategoryMapper;
import com.foodsafety.model.Category;
import com.foodsafety.model.Product;
import com.foodsafety.model.SubCategory;
import com.foodsafety.repository.ProductRepository;
import com.foodsafety.repository.SubCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BeneficiaryRequestContainsProductMapper beneficiaryRequestContainsProductMapper;
    private ProductMapper productMapper;
    private SubCategoryMapper subCategoryMapper;


    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product: {}", id);
        Product product = productRepository.getReferenceById(id);
        ProductDTO productDTO = productMapper.fromEntityToDTO(product);
        productDTO.setSubCategory_id(product.getSubCategory().getSubCategoryId());
        //productDTO.setBeneficiaryRequestContainsProducts(requestContainsProductMapper.fromEntitiesToDTOList(product.getBeneficiaryRequestContainsProducts()));

        return productDTO;
    }

    public List<ProductDTO> findAll() {
        try {
            List<ProductDTO> productDTOS = productRepository.findAllAsDto();
            // List<ProductDTO> productDTOS = productMapper.fromEntitiesToDTOList(products);

            log.info("Products gotted successfully");
            return productDTOS;
        } catch (Exception e) {
            log.error("Cannot get Products from source", e);
            return null;
        }
    }

    public List<ProductDTO> findAllByGroupId(String groupId) {
        try {
            List<Product> productList = productRepository.findAllByGroupId(groupId);
            List<ProductDTO> productDTOS = productMapper.fromEntitiesToDTOList(productList);

            log.info("Products gotten successfully");
            return productDTOS;
        } catch (Exception e) {
            log.error("Cannot get products from source", e);
            return null;
        }
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategory_id()).get();

            log.debug("Request to save Product: {}", productDTO);
            Product product = productMapper.fromDTOToEntity(productDTO);
            product.setSubCategory(subCategory);
            // product.setRequestContainsProduct(containsProduct);
            product = productRepository.saveAndFlush(product);
            log.info("Product added successfully ");
            ProductDTO productDTO1 = productMapper.fromEntityToDTO(product);
            productDTO1.setSubCategory_id(subCategory.getSubCategoryId());
            // productDTO1.setBeneficiaryRequestContainsProducts(requestContainsProductMapper.fromEntitiesToDTOList(product.getBeneficiaryRequestContainsProducts()));
            return productDTO1;
        } catch (Exception e) {
            log.error("Cannot add Product ", e);
            return null;
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        try {
            SubCategory subCategory = subCategoryRepository.findById(productDTO.getSubCategory_id()).get();
            Optional<Product> optional = productRepository.findById(productDTO.getProductId());
            if (optional.isPresent()) {
                Product product = optional.get();
                product.setProductName(productDTO.getProductName());

                product.setUnitPrice(productDTO.getUnitPrice());
                product.setUnit(productDTO.getUnit());
                product.setSubCategory(subCategory);
                //product.setBeneficiaryRequestContainsProducts(requestContainsProductMapper.fromDTOListToEntities(productDTO.getBeneficiaryRequestContainsProducts()));
                productRepository.save(product);
                log.info("Product with id= {} edited successfully", product.getProductId());
                return productMapper.fromEntityToDTO(product);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit Product ", e);
            return null;
        }
    }

    public List<ProductDTO> getAllProductsBySubCategory(Long id) {

        List<ProductDTO> products = productRepository.findBySubCategorySubCategoryId(id);
      /*  List<ProductDTO> productDTOS = productMapper.fromEntitiesToDTOList(
                productRepository.findBySubCategorySubCategoryId(idSubCategory));
        return productDTOS;*/
        return products;
    }

    public List<ProductDTO> getAllProductsBySubCategoryName(String nom) {

        List<ProductDTO> productDTOS = productMapper.fromEntitiesToDTOList(
                productRepository.findBySubCategorySubcategoryName(nom));

        return productDTOS;
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
        log.info("Product removed successfully");
    }


}