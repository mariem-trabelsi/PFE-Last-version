package com.foodsafety.service;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.mapper.CategoryMapper;
import com.foodsafety.mapper.SubCategoryMapper;
import com.foodsafety.model.Category;
import com.foodsafety.repository.CategoryRepository;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private SubCategoryMapper subCategoryMapper;

    public CategoryDTO findOne(Long id) {
        log.debug("Request to get Category: {}", id);
        Category category = categoryRepository.getReferenceById(id);
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1 = categoryMapper.fromEntityToDTO(category);
        categoryDTO1.setSubCategoryDTOS(subCategoryMapper.fromEntitiesToDTOList(category.getSubCategories()));
        return categoryDTO1;
    }

//    public CategoryDTO getCategoryByCategoryIdAndGroupId(Long id, String groupId) {
//        log.debug("Request to get Category: {}", id);
//        Category category = categoryRepository.getCategoryByCategoryIdAndGroupId(id, groupId);
//        CategoryDTO categoryDTO1 = new CategoryDTO();
//        categoryDTO1 = categoryMapper.fromEntityToDTO(category);
//        categoryDTO1.setSubCategoryDTOS(subCategoryMapper.fromEntitiesToDTOList(category.getSubCategories()));
//        return categoryDTO1;
//    }

    public List<CategoryDTO> findAll() {
        try {
            List<Category> categoryList = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOS = categoryMapper.fromEntitiesToDTOList(categoryList);


            log.info("Categories gotted successfully");
            return categoryDTOS;
        } catch (Exception e) {
            log.error("Cannot get categories from source", e);
            return null;
        }
    }
    public List<CategoryDTO> findAllByGroupId(String groupId) {
        try {
            List<Category> categoryList = categoryRepository.findAllByGroupId(groupId);
            List<CategoryDTO> categoryDTOS = categoryMapper.fromEntitiesToDTOList(categoryList);

            log.info("Categories gotten successfully");
            return categoryDTOS;
        } catch (Exception e) {
            log.error("Cannot get categories from source", e);
            return null;
        }
    }


    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        try {
            log.debug("Request to save Category: {}", categoryDTO);
            Category category = categoryMapper.fromDTOToEntity(categoryDTO);
            category = categoryRepository.saveAndFlush(category);
            log.info("category added successfully  ");
            CategoryDTO categoryDTO1 = new CategoryDTO();
            categoryDTO1 = categoryMapper.fromEntityToDTO(category);
            // categoryDTO1.setSubCategoryDTOS(subCategoryMapper.fromEntitiesToDTOList(category.getSubCategories()));
            return categoryDTO1;
        } catch (Exception e) {
            log.error("Cannot add Category ", e);
            return null;
        }
    }


    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getCategoryId());
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setCategoryName(categoryDTO.getCategoryName());

                categoryRepository.save(category);
                log.info("Category with id= {} edited successfully", category.getCategoryId());
                return categoryMapper.fromEntityToDTO(category);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Cannot edit Category ", e);
            return null;
        }
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("Category removed successfully");
    }

}
/*public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }*/

    /*public void createCategory(Category category) {
        this.categoryRepository.save(category);
    }public String updateCategory(@PathVariable Long id, @RequestBody Category newCategory) {
         Category category = categoryRepository.findById(id).get();
         category.setCategoryName(newCategory.getCategoryName());
         this.categoryRepository.save(category);
         return "modifié avec succès";
     }*/