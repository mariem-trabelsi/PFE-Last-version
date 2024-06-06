package com.foodsafety.controller;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.ProductDTO;
import com.foodsafety.dto.SubCategoryDTO;
import com.foodsafety.model.Product;
import com.foodsafety.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/product")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*@GetMapping("/")
    public String welcome() {
        return myAppBean.getMessage() +
                ", user count is" + myAppBean.getUsercount()
                + ",Deployed in:" + myAppBean.getEnv()
                + "," + myAppBean.getThankyoumessage();
    }

    }*/
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO productDTO = productService.findOne(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<ProductDTO>> findAllByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok(productService.findAllByGroupId(groupId));
    }

    @PostMapping
    public ResponseEntity<ProductDTO>
    createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(productService.createProduct(productDTO));
    }


    @GetMapping(value = "/subCategory/{idSubCategory}")
    public ResponseEntity< List<ProductDTO>> getAllProductsBySubCategory(@PathVariable Long idSubCategory) {
     List <ProductDTO> product =   productService.getAllProductsBySubCategory(idSubCategory);
        return ResponseEntity.ok(product);
    }
    @GetMapping(value = "/subCategory/name/{name}")
    public ResponseEntity< List<ProductDTO>> getAllProductsBySubCategoryName(@PathVariable String name) {
     List <ProductDTO> productDTO =   productService.getAllProductsBySubCategoryName(name);
        return ResponseEntity.ok( productDTO);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(id);
        return ResponseEntity.ok().body( productService.updateProduct(productDTO));
    }
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.ok().build();
    }


}