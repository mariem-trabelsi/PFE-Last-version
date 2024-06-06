package com.foodsafety.service;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.mapper.CategoryMapper;
import com.foodsafety.mapper.ProductMapper;
import com.foodsafety.mapper.SubCategoryMapper;
import com.foodsafety.model.Category;
import com.foodsafety.model.SubCategory;
import com.foodsafety.repository.CategoryRepository;
import com.foodsafety.repository.SubCategoryRepository;
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
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;


    public SubCategoryDTO findOne(Long id) {
        log.debug("Request to get Category: {}", id);
        SubCategory subCategory = subCategoryRepository.getReferenceById(id);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.fromEntityToDTO(subCategory);
        subCategoryDTO.setProductDTOS(productMapper.fromEntitiesToDTOList(subCategory.getProducts()));
        subCategoryDTO.setCategory_id(subCategory.getCategory().getCategoryId());
        //System.out.println("category :: *******"+ subCategory.getCategory().getCategoryId());
        return subCategoryDTO;

    }

    public List<SubCategoryDTO> findAll() {
        try {
            List<SubCategoryDTO> subCategoryDTOList = subCategoryRepository.findAllAsDto();
            log.info("SubCategories gotted successfully");
            return subCategoryDTOList;
        } catch (Exception e) {
            log.error("Cannot get SubCategories from source", e);
            return null;
        }
    }

    public List<SubCategoryDTO> findAllByGroupId(String groupId) {
        try {
            List<SubCategory> subCategoryList = subCategoryRepository.findAllByGroupId(groupId);
            List<SubCategoryDTO> subCategoryDTOS = subCategoryMapper.fromEntitiesToDTOList(subCategoryList);

            log.info("SubCategories gotten successfully");
            return subCategoryDTOS;
        } catch (Exception e) {
            log.error("Cannot get subCategories from source", e);
            return null;
        }
    }

    public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
        try {
            Category category = categoryRepository.findById(subCategoryDTO.getCategory_id()).get();
            log.debug("Request to save SubCategory: {}", subCategoryDTO);
            SubCategory subCategory = subCategoryMapper.fromDTOToEntity(subCategoryDTO);
            subCategory.setCategory(category);
            subCategory = subCategoryRepository.saveAndFlush(subCategory);
            log.info("SubCategory added successfully ");
            SubCategoryDTO subCategoryDTO1 = subCategoryMapper.fromEntityToDTO(subCategory);
            subCategoryDTO1.setCategory_id(category.getCategoryId());
            return subCategoryDTO1;
        } catch (Exception e) {
            log.error("Cannot add SubCategory ", e);
            return null;
        }
    }

    public SubCategoryDTO updateSubCategory(SubCategoryDTO subCategoryDTO) {
        try {
            Category category = categoryRepository.findById(subCategoryDTO.getCategory_id()).get();
            Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(subCategoryDTO.getSubCategoryId());
            if (optionalSubCategory.isPresent()) {
                SubCategory subCategory = optionalSubCategory.get();
                subCategory.setSubcategoryName(subCategoryDTO.getSubcategoryName());
                subCategory.setCategory(category);
                subCategoryRepository.save(subCategory);
                log.info("SubCategory with id= {} edited successfully", subCategory.getSubCategoryId());
                return subCategoryMapper.fromEntityToDTO(subCategory);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit SubCategory ", e);
            return null;
        }
    }

    public void removeSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
        log.info("SubCategory removed successfully");

    }

    public List<SubCategoryDTO> getAllSubCategoryByCategory(Long idCategory) {

        List<SubCategoryDTO> subCategoryDTOS = subCategoryMapper.fromEntitiesToDTOList(
                subCategoryRepository.findByCategoryCategoryId(idCategory));

        return subCategoryDTOS;
    }

}	