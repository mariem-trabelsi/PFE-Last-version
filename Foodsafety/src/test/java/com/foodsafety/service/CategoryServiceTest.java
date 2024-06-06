package com.foodsafety.service;

import com.foodsafety.dto.CategoryDTO;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CategoryServiceTest {
    @Autowired
    private CategoryService CatService;
    @Test
    public void shouldSaveCategoryWithSucces(){

        /* Test de la méthode CREATE  */

        CategoryDTO excpectedCategory;

        excpectedCategory = CategoryDTO.builder()
        		.categoryName("BOISSONS")
                .build();

        CategoryDTO savedCateg = CatService.createCategory(excpectedCategory);}


        /* Test de la méthode FIND ALL  */
	    @Test
	    public void shouldFindALLwithSuccess(){
	        List<CategoryDTO> findCateg =  CatService.findAll();
	        for (CategoryDTO obj:findCateg){
	        	System.out.println(obj.getCategoryName());
	        }
	    }

	    /* Test de la méthode FIND ONE  */
        @Test
        public void shouldFindOneWithSucces(){
	        CategoryDTO findOne = CatService.findOne(2L);
	        System.out.println(findOne.getCategoryName());
        }

        /* Test de la méthode REMOVE  */
        /* Changer la variable de l'ID si elle n'existe pas  */
        @Test
        public void shouldRemoveWithSucces(){
        	CatService.removeCategory(17L);
        }



        /* Test de la méthode UPDATE  */
        @Test
        public void shouldUpdateWithSucces(){
	        CategoryDTO updateCategory;
	        updateCategory = CategoryDTO.builder()
	                .categoryId(13L)
	                .categoryName("Elyess")
	                .build();
	        CategoryDTO upCateg = CatService.updateCategory(updateCategory);
        }


}