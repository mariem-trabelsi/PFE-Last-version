package com.foodsafety.controller;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/category")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {

        CategoryDTO categoryDTO = categoryService.findOne(id);
        return ResponseEntity.ok().body(categoryDTO);
    }

//    @Transactional(readOnly = true)
//    @GetMapping("{id}/groupId/{groupId}")
//    public ResponseEntity<CategoryDTO> getCategoryByIdAndGroupId(@PathVariable Long id, @PathVariable String groupId) {
//        CategoryDTO categoryDTO = categoryService.getCategoryByCategoryIdAndGroupId(id, groupId);
//        return ResponseEntity.ok().body(categoryDTO);
//    }
//
    @Transactional(readOnly = true)
    @GetMapping("{id}/subCategories")
    public ResponseEntity<List<SubCategoryDTO>> getSubCategories(@PathVariable Long id) {
    	List<SubCategoryDTO> subCategories = categoryService.findOne(id).getSubCategoryDTOS();
        return ResponseEntity.ok().body(subCategories);
    }

//    @Transactional(readOnly = true)
//    @GetMapping("{id}/groupId/{groupId}/subCategories")
//    public ResponseEntity<List<SubCategoryDTO>> getSubCategoriesByGroupId(@PathVariable Long id, @PathVariable String groupId) {
//        List<SubCategoryDTO> subCategories = categoryService.getCategoryByCategoryIdAndGroupId(id, groupId).getSubCategoryDTOS();
//        return ResponseEntity.ok().body(subCategories);
//    }


    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<CategoryDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(categoryService.findAllByGroupId(groupId));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setCategoryId(id);
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
        return ResponseEntity.ok().build();
    }
}
	