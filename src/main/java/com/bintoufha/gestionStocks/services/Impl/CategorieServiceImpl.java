package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.repository.CategorieRepository;
import com.bintoufha.gestionStocks.services.CategorieService;
import com.bintoufha.gestionStocks.validator.CategorieValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    private final DefaultErrorAttributes errorAttributes;
    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieServiceImpl(
            CategorieRepository categorieRepository, DefaultErrorAttributes errorAttributes
    ){
        this.categorieRepository = categorieRepository;
        this.errorAttributes = new DefaultErrorAttributes();
    }

    @Override
    public CategoriesDto save(CategoriesDto dto) {
        List<String> errors = CategorieValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Cette categorie est invalide",dto);
            throw new InvalEntityException(
                    "cette categorie est invalide", ErrorCodes.CATEGORY_NOT_VALID,errors);
        }
        
        return CategoriesDto.fromEntity(

                categorieRepository.save(CategoriesDto.toEntity(dto))
        );

    }

    @Override
    public CategoriesDto findByUuid(UUID uuid) {
        if(uuid == null){
            log.error("identifiant est null");
            return null;
        }

        Optional<Categories> categories = categorieRepository.findByUuid(uuid);

        return categories
                .map(CategoriesDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "la categorie avec identifiant : " + uuid + "n'existe pas dans la base de donnee",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    @Override
    public CategoriesDto findByCode(String code) {
        return null;
    }

    @Override
    public List<CategoriesDto> findAll() {
        return categorieRepository.findAll().stream()
                .map(CategoriesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {

    }
}
