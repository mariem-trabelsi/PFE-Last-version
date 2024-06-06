package com.foodsafety.service;

import com.foodsafety.dto.ProductDTO;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService prodService;

    /* Test de la méthode CREATE  */
    @Test
    public void shouldSaveProduitWithSucces() {
        ProductDTO expectedProd;


        expectedProd = ProductDTO.builder()
                .subCategory_id(1L)
                .productName("caffe")
                .unitPrice(0)
                .unit("KG")
                .build();

        ProductDTO savedProduct = prodService.createProduct(expectedProd);
    }

    /* Test de la méthode FIND ALL  */
    @Test
    public void shouldFindProductWithSucces(){
        List<ProductDTO> allProduct = prodService.findAll();
        for (ProductDTO p : allProduct){
            System.out.println(p.getProductName());
        }
    }

    /* Test de la méthode FIND ONE  */

    @Test
    public void shouldFindOneProductWithSuccess(){
        ProductDTO findOne = prodService.findOne(3L);
        System.out.println(findOne.getProductName());
    }


    /* Test de la méthode GetAll by sub Categ ID  */
    @Test
    public void shouldGetProdBySubCategID(){
        List<ProductDTO> ProdBySubID = prodService.getAllProductsBySubCategory(1L);
        for (ProductDTO p : ProdBySubID){
            System.out.println(p.getProductName());
        }

    }

    /* Test de la méthode GetAll by sub Categ NAME  */
    @Test
    public void shouldGetProdBySubCategName () {

        List<ProductDTO> prodBySubName = prodService.getAllProductsBySubCategoryName("Pâtes");

        for (ProductDTO p : prodBySubName ){
            System.out.println(p.getProductName());
        }
    }

    /* Test de la méthode remove , Mettre un id qui existe */
    @Test
    public void shouldRemoveWithSucces () {
        prodService.removeProduct(75L);
    }

    /* Test de la méthode UPDATE (Mettre un product ID qui existe avec son SubCateg ID */
    @Test
    public void shouldUpdateWithSucces(){
        ProductDTO expectedProd;

        expectedProd = ProductDTO.builder()
                .productId(76L)
                .subCategory_id(1L)
                .productName("caffe")
                .unitPrice(0)
                .unit("LITRE")
                .build();

        ProductDTO updateProduct = prodService.updateProduct(expectedProd);

    }

}
