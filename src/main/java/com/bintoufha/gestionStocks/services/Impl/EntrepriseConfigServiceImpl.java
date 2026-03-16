package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.CategorieMapper;
import com.bintoufha.gestionStocks.mapper.EntrepriseMapper;
import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.model.EntrepriseConfig;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.repository.CategorieRepository;
import com.bintoufha.gestionStocks.repository.EntrepriseConfigRepository;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.EntrepriseConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseConfigServiceImpl implements EntrepriseConfigService {
    private final EntrepriseConfigRepository entrepriseConfigRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final CategorieRepository categorieRepository;
    private final UtilisateursRepository utilisateursRepository;
    private final EntrepriseMapper mapper;

    public EntrepriseConfigServiceImpl(EntrepriseConfigRepository entrepriseConfigRepository, EntrepriseRepository entrepriseRepository, CategorieRepository categorieRepository, UtilisateursRepository utilisateursRepository, EntrepriseMapper mapper) {
        this.entrepriseConfigRepository = entrepriseConfigRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.categorieRepository = categorieRepository;
        this.utilisateursRepository = utilisateursRepository;
        this.mapper = mapper;
    }

    @Override
    public EntrepriseConfigSaveDto save(EntrepriseConfigSaveDto configDto) {

        Entreprises entreprises =
                entrepriseRepository.findByUuid(configDto.getEntreprise().getUuid())
                        .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
        Categories categorie =
                categorieRepository.findByUuid(configDto.getCategorie().getUuid())
                        .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        Utilisateurs user =
                utilisateursRepository.findByUuid(configDto.getConfigurePar().getUuid())
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        EntrepriseConfig tarificationEntreprise =
                entrepriseConfigRepository.findByEntrepriseAndCategorie(entreprises, categorie)
                        .orElse(new EntrepriseConfig());
        tarificationEntreprise.setEntreprise(entreprises);
        tarificationEntreprise.setCategorie(categorie);
        tarificationEntreprise.setMargeGros(configDto.getMargeGros());
        tarificationEntreprise.setMargeDetail(configDto.getMargeDetail());
        tarificationEntreprise.setConfigurePar(user);
        tarificationEntreprise.setActive(true);
        return mapper.fromEntityEntrepriseConfig(entrepriseConfigRepository.save(tarificationEntreprise));
    }

    @Override
    public EntrepriseConfigListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant est introuvable");
            return null;
        }

        // Récupération de la categorie ou exception si non trouvé
        EntrepriseConfig entrepriseConfig = entrepriseConfigRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun configuration avec uuid" + uuid + "n'a été trouve dans la base de donnée",
                        ErrorCodes.CONFIG_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return EntrepriseMapper.toListDtoEntrepriseConfig(entrepriseConfig);
    }

    @Override
    public List<EntrepriseConfigListDto> findAll() {
        return entrepriseConfigRepository.findAll()
                .stream()
                .map(config -> {
                    // ou via TarificationService si besoin
                    return EntrepriseMapper.toListDtoEntrepriseConfig(config);
                })
                .toList();
    }

    @Override
    public void desactiverTarification(UUID uuid) {
        EntrepriseConfig tarificationEntreprise = entrepriseConfigRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Tarification non trouvée"));
        tarificationEntreprise.setActive(false);
        entrepriseConfigRepository.save(tarificationEntreprise);
    }

    @Override
    public void activerTarification(UUID uuid) {
        EntrepriseConfig tarificationEntreprise = entrepriseConfigRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Tarification non trouvée"));
        tarificationEntreprise.setActive(true);
        entrepriseConfigRepository.save(tarificationEntreprise);
    }

    @Override
    public List<EntrepriseConfigListDto> getTarificationsByBoutique(UUID entrepriseUuid) {
        Entreprises entreprises = entrepriseRepository.findByUuid(entrepriseUuid).orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
        List<EntrepriseConfig> configs = entrepriseConfigRepository.findByEntreprises(entreprises);
        return mapper.toLisEntrepriseConfigDto(configs); // ✅ liste complète
    }

    @Override
    public List<EntrepriseConfigListDto> getTarificationsActivesByBoutique(UUID entrepriseUuid) {
        Entreprises entreprises = entrepriseRepository.findByUuid(entrepriseUuid).orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
        List<EntrepriseConfig> tarifications = entrepriseConfigRepository.findByEntrepriseAndActiveTrue(entreprises);
        return mapper.toLisEntrepriseConfigDto(tarifications); // ✅ liste complète
    }

    @Override
    public EntrepriseConfigListDto getTarificationByBoutiqueAndCategorie(UUID entrepriseUuid, UUID categorieUuid) {
        if (entrepriseUuid == null || categorieUuid == null  ) {
            log.error("Identifiant est introuvable");
            return null;
        }

        EntrepriseConfig entrepriseConfig =  entrepriseConfigRepository.findByEntrepriseUuidAndCategorieUuid(entrepriseUuid, categorieUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun configuration avec uuid" + entrepriseUuid + "n'a été trouve dans la base de donnée",
                        ErrorCodes.CONFIG_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return EntrepriseMapper.toListDtoEntrepriseConfig(entrepriseConfig);
    }

    @Override
    public BigDecimal calculerPrix(UUID entrepriseUuid, UUID categorieUuid, BigDecimal prixAchat,String typePrix) {
        EntrepriseConfig tarificationEntreprise =
                entrepriseConfigRepository.findByEntrepriseUuidAndCategorieUuid(entrepriseUuid, categorieUuid)
                        .orElseThrow(() -> new RuntimeException("Tarification non trouvée"));
        return tarificationEntreprise.calculerPrixGros(prixAchat);
    }

}
