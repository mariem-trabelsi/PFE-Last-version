package com.foodsafety.service;

import com.foodsafety.dto.SubCategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SubCategoryServiceTest {
    @Autowired
    private SubCategoryService service;
    @Test
    public void createSub(){
        SubCategoryDTO expectedSubCategory;
        expectedSubCategory = SubCategoryDTO.builder()
                .category_id(1L)
                .subcategoryName("elyess")
                .build();

        SubCategoryDTO savedSub = service.createSubCategory(expectedSubCategory);
    }
    @Test
    public void findAllSub(){

        List<SubCategoryDTO> findCate = service.findAll();
        for(SubCategoryDTO obj : findCate){
            System.out.println(obj.getSubcategoryName());
        }

        /*SubCategoryDTO expectedSubCategory;
        expectedSubCategory = SubCategoryDTO.builder()
                .category_id(1L)
                .subcategoryName("elyess")
                .build();

        SubCategoryDTO savedSub = service.createSubCategory(expectedSubCategory);

      SubCategoryDTO findOneCategory = service.findOne(1L);

        assertNotNull(findOneCategory);
        assertNotNull(findOneCategory.getSubCategoryId());
        assertEquals(findOneCategory.getSubCategoryId() ,expectedSubCategory.getSubCategoryId() );
        assertEquals(expectedSubCategory.getSubcategoryName() , findOneCategory.getSubcategoryName());
*/
    }

    @Test
    public void findOneSub() {
        SubCategoryDTO findOne = service.findOne(1L);

            System.out.println(findOne.getSubcategoryName());


    }
   /* @Test
    public void removeOneSub() {
        SubCategoryDTO del;
        del = SubCategoryDTO.builder().
                category_id(1L).subcategoryName("elyess")
                .build();
        SubCategoryDTO d =  service.removeSubCategory(del);
    }
*/
    @Test
    public void updateOneSub() {
        SubCategoryDTO updateSub ;
        updateSub = SubCategoryDTO.builder()
         .category_id(1L).subcategoryName("elyess")
                .build();

        SubCategoryDTO up = service.updateSubCategory(updateSub);

    }

    @Test
    public void getAllSubCategoryByCategory(){
        List<SubCategoryDTO> subByCate = service.getAllSubCategoryByCategory(1L);
        for (SubCategoryDTO c : subByCate){
            System.out.println(c.getSubcategoryName());
        }
    }

    }