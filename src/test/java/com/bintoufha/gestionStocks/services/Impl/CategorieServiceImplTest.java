package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.services.CategorieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategorieServiceImplTest {

    @Autowired
    private CategorieService service;

    @Test // test pour insertion de la categorie
    void save() {

        CategoriesDto exceptedCategorie = CategoriesDto.builder()
                .code("cat test")
                .designation("designantion")
                .build();
        CategoriesDto savedCategorie = service.save(exceptedCategorie);

        assertNotNull(savedCategorie);
       // assertNotNull(savedCategorie.getUuid());
        assertEquals(exceptedCategorie.getCode(),savedCategorie.getCode());
        assertEquals(exceptedCategorie.getDesignation(),savedCategorie.getDesignation());
    }
    @Test //test pour la modification de la categorie
    void update() {

        CategoriesDto exceptedCategorie = CategoriesDto.builder()
                .code("cat test")
                .designation("designantion")
                .build();
        CategoriesDto savedCategorie = service.save(exceptedCategorie);

        CategoriesDto categoriesUpdate = savedCategorie;
        categoriesUpdate.setCode("Cat update");

        savedCategorie = service.save(categoriesUpdate);

        assertNotNull(categoriesUpdate);
       // assertNotNull(categoriesUpdate.getUuid());
        assertEquals(categoriesUpdate.getCode(),savedCategorie.getCode());
        assertEquals(categoriesUpdate.getDesignation(),savedCategorie.getDesignation());

    }
    @Test // test au cas ou si utilisateur modifier une valeur qui n'est pas entendu
    void InvalidEntityException() {

        CategoriesDto exceptedCategorie = CategoriesDto.builder().build();
        InvalEntityException exceptedException =
                assertThrows(InvalEntityException.class, () -> service.save(exceptedCategorie));

        assertEquals(ErrorCodes.CATEGORY_NOT_VALID,exceptedException.getErrorCodes());
        assertNotNull(exceptedException.getErrors());
        assertEquals("Veuillez renseigner le code de la categorie",exceptedException.getErrors().get(0));

    }

 //  @Test
//    void EntityNotFoundException() {
//
//        EntityNoFoundException exceptedException =
//                assertThrows(EntityNoFoundException.class, () -> service.findByUuid(
//                        UUID.fromString("4588c802-8272-11f0-b6cb-9d9f53b1868b")));
//
//        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND,exceptedException.getErrorCodes());
//        assertEquals("Veuillez renseigner le code de la categorie",exceptedException.getErrorCodes());
//
//    }

    @Test
    void EntityNotFoundException2() {
        service.findByUuid(UUID.fromString("feb44fb4-104d-406c-bea5-9202538c29ef"));


    }

}