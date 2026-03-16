package com.bintoufha.gestionStocks.model.auth;

import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.*;
import com.bintoufha.gestionStocks.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateursRepository userRepository;

    @Autowired
    private TypeEntrepriseRepository typeBoutiqueRepository;

    @Autowired
    private EntrepriseRepository boutiqueRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Initialiser les permissions
        permissionService.initializeDefaultPermissions();

        // 2. Créer les rôles par défaut
        createDefaultRoles();

        // 3. Créer un super admin
        createSuperAdmin();

        // 4. Créer des données de test
        createTestData();
    }

    private void createDefaultRoles() {
        // Rôle SUPER_ADMIN (global)
        if (!roleRepository.existsByName("SUPER_ADMIN")) {
            Roles superAdmin = new Roles();
            superAdmin.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("SUPER_ADMIN")
                    .build());
            superAdmin.setDescription("Administrateur système avec tous les droits");
            superAdmin.setScope(RoleScope.GLOBAL);
            roleRepository.save(superAdmin);
        }

        // Rôle ADMIN_GENERAL (global)
        if (!roleRepository.existsByName("ADMIN_GENERAL")) {
            Roles adminGeneral = new Roles();
            adminGeneral.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("ADMIN_GENERAL")
                    .build());
            adminGeneral.setDescription("Administrateur général multi-boutiques");
            adminGeneral.setScope(RoleScope.GLOBAL);
            roleRepository.save(adminGeneral);
        }

        // Rôle ADMIN_BOUTIQUE (boutique)
        if (!roleRepository.existsByName("ADMIN_BOUTIQUE")) {
            Roles adminBoutique = new Roles();
            adminBoutique.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("ADMIN_BOUTIQUE")
                    .build());
            adminBoutique.setDescription("Administrateur d'une boutique spécifique");
            adminBoutique.setScope(RoleScope.ENTREPRISES);
            roleRepository.save(adminBoutique);
        }

        // Rôle RESPONSABLE_STOCK (boutique)
        if (!roleRepository.existsByName("RESPONSABLE_STOCK")) {
            Roles responsableStock = new Roles();
            responsableStock.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("RESPONSABLE_STOCK")
                    .build());
            responsableStock.setDescription("Responsable du stock d'une boutique");
            responsableStock.setScope(RoleScope.ENTREPRISES);
            roleRepository.save(responsableStock);
        }

        // Rôle EMPLOYE_STOCK (boutique)
        if (!roleRepository.existsByName("EMPLOYE_STOCK")) {
            Roles employeStock = new Roles();
            employeStock.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("EMPLOYE_STOCK")
                    .build());
            employeStock.setDescription("Employé chargé du stock");
            employeStock.setScope(RoleScope.ENTREPRISES);
            roleRepository.save(employeStock);
        }

        // Rôle EMPLOYE_VENTE (boutique)
        if (!roleRepository.existsByName("EMPLOYE_VENTE")) {
            Roles employeVente = new Roles();
            employeVente.setUtilisateurs(Utilisateurs.builder()
                    .nomPrenomUtilisateurs("EMPLOYE_VENTE")
                    .build());
            employeVente.setDescription("Employé de vente/caissier");
            employeVente.setScope(RoleScope.ENTREPRISES);
            roleRepository.save(employeVente);
        }
    }

    private void createSuperAdmin() {
        if (!userRepository.existsByUsername("superadmin")) {
            Utilisateurs superAdmin = new Utilisateurs();
            superAdmin.setNomPrenomUtilisateurs("superadmin");
            superAdmin.setPwd(passwordEncoder.encode("8345karifa@@"));
            superAdmin.setEmail("superadmin@gestionstock.com");
            superAdmin.setActif(true);

            // Récupérer le rôle SUPER_ADMIN
            Roles superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rôle SUPER_ADMIN non trouvé"));

            superAdmin.setRoles(new HashSet<>(Arrays.asList(superAdminRole)));
            userRepository.save(superAdmin);
            System.out.println("Super admin créé avec succès");
        }
    }

    private void createTestData() {
        // Créer des types de boutique
        TypeEntreprises supermarche = createTypeIfNotExists("SUPERMARCHE", "Supermarché", "SM");
        TypeEntreprises boutique = createTypeIfNotExists("BOUTIQUE", "Boutique spécialisée", "BTQ");
        TypeEntreprises depot = createTypeIfNotExists("DEPOT", "Dépôt grossiste", "DEP");

        // Créer des boutiques
        createBoutiqueIfNotExists("Carrefour Paris 15", supermarche, "15 Rue de Commerce", "Paris", "75015");
        createBoutiqueIfNotExists("Carrefour Lyon", supermarche, "10 Rue de la République", "Lyon", "69001");
        createBoutiqueIfNotExists("Boutique Électronique", boutique, "5 Avenue des Technologies", "Paris", "75008");

        // Créer des catégories
        createCategorieIfNotExists("Électronique", "ELEC", "Produits électroniques");
        createCategorieIfNotExists("Alimentation", "FOOD", "Produits alimentaires");
        createCategorieIfNotExists("Textile", "TEXT", "Vêtements et textiles");
        createCategorieIfNotExists("Cosmétiques", "COSM", "Produits cosmétiques");

        System.out.println("Données de test créées avec succès");
    }

    private TypeEntreprises createTypeIfNotExists(String nom, String description, String code) {
        return typeBoutiqueRepository.findByNomTypeEntreprise(nom)
                .orElseGet(() -> {
                    TypeEntreprises type = new TypeEntreprises();
                    type.setNomTypeEntreprise(nom);
                    type.setDescription(description);
                    type.setCode(code);
                    return typeBoutiqueRepository.save(type);
                });
    }

    private void createBoutiqueIfNotExists(String nom, TypeEntreprises type, String adresse, String ville, String codePostal) {
        if (!boutiqueRepository.existsByNomEntreprise(nom)) {
            Entreprises boutique = new Entreprises();
            boutique.setNomEntreprise(nom);
            boutique.setTypeEntreprises(type);
            boutique.setNumero("83 45 71 63");
            boutique.setEmail("contact@" + nom.toLowerCase().replace(" ", "") + ".com");
            boutique.setStatut(StatutEntreprise.ACTIVE);
            boutique.setAddresse(
                    Addresse.builder()
                            .Addresse1(boutique.getAddresse().getAddresse1())
                            .Addresse2(boutique.getAddresse().getAddresse2())
                            .Ville(boutique.getAddresse().getVille())
                            .Pays(boutique.getAddresse().getPays())
                            .CodePostale(boutique.getAddresse().getCodePostale())
                            .build()
            );

            boutiqueRepository.save(boutique);
        }
    }

    private void createCategorieIfNotExists(String nom, String code, String description) {
        if (!categorieRepository.existsByNom(nom)) {
            Categories categorie = new Categories();
            categorie.setDesignation(nom);
            categorie.setCode(code);
            categorieRepository.save(categorie);
        }
    }
}
