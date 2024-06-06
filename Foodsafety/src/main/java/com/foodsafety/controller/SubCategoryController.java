package com.foodsafety.controller;
import com.foodsafety.dto.ProductDTO;
import com.foodsafety.dto.SubCategoryDTO;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foodsafety.model.SubCategory;
import com.foodsafety.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/subCategory")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;


    @Transactional( readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDTO> getSubCategory(@PathVariable Long id) {
        SubCategoryDTO subCategoryDTO = subCategoryService.findOne(id);
        return ResponseEntity.ok().body(subCategoryDTO);
    }
    
    @Transactional(readOnly = true)
    @GetMapping("{id}/products")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable Long id) {
    	List<ProductDTO> products = subCategoryService.findOne(id).getProductDTOS();
        return ResponseEntity.ok().body(products);
    }

    @Transactional ( readOnly = true)
    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> findAll() {
        return ResponseEntity.ok(subCategoryService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<SubCategoryDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(subCategoryService.findAllByGroupId(groupId));
    }

    @PostMapping
    public ResponseEntity<SubCategoryDTO> createsubCategory(@RequestBody SubCategoryDTO subCategoryDTO) {
        return ResponseEntity.ok().body(subCategoryService.createSubCategory(subCategoryDTO));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<SubCategoryDTO> updatesubCategory(@PathVariable Long id, @RequestBody SubCategoryDTO subCategoryDTO) {
    	subCategoryDTO.setSubCategoryId(id);
        return ResponseEntity.ok().body(subCategoryService.updateSubCategory(subCategoryDTO));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSubCategory(@PathVariable Long id) {
        subCategoryService.removeSubCategory(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/category/{idCategory}")
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategoryByCategory (@PathVariable Long idCategory){
        List<SubCategoryDTO> subCategoryDTOS = subCategoryService.getAllSubCategoryByCategory(idCategory);
        return ResponseEntity.ok(subCategoryDTOS);
    }

}
	