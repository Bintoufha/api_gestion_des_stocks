package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.utilisateur.ChangerMotDePasseUtilisateurDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
@RestController
public interface UtilisateursApi {

    @PostMapping(value = APP_ROOT + "/utilisateurs/create_utilisateur",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<UtilisateurSaveDto> save(@RequestBody UtilisateurSaveDto utilisateursDto);

    @PostMapping(value = APP_ROOT + "/utilisateurs/update-pwd",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or (hasRole('ADMIN_BOUTIQUE')")
    ResponseEntity<UtilisateurSaveDto> ChangePwd(@RequestBody ChangerMotDePasseUtilisateurDto pwdChange);

    @GetMapping(value = APP_ROOT + "/utilisateurs/recherche/uuid_utilisateur",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or " +
            "(hasRole('ADMIN_BOUTIQUE') and @securityService.isMyBoutiqueUser(#uuid_utilisateur)")
    ResponseEntity<UtilisateurListDto> findByUuid(@PathVariable("uuid_utilisateur") UUID uuid);

    @GetMapping(value = APP_ROOT + "/utilisateurs/email/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or (hasRole('ADMIN_BOUTIQUE')")
    ResponseEntity<UtilisateurByEmailDto> findByEmail(@PathVariable("email") String email);


    @GetMapping(value = APP_ROOT + "/utilisateurs/All_utilisateur",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or (hasRole('ADMIN_BOUTIQUE')")
    ResponseEntity<List<UtilisateurListDto>> findAll();

    @DeleteMapping(value = APP_ROOT + "/utilisateurs/uuid_utilisateur",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or " +
            "(hasRole('ADMIN_BOUTIQUE') and @securityService.isMyBoutiqueUser(#uuid_utilisateur)")
    ResponseEntity<Void> deleteByUuid(@PathVariable("uuid_utilisateur") UUID uuid);

    @GetMapping(value = APP_ROOT + "/utilisateurs/entreprise/{uuid_entreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
     )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or (hasRole('ADMIN_BOUTIQUE') and @securityService.isMyBoutique(#boutiqueId))")
    public ResponseEntity<List<UtilisateurListDto>> getUsersByBoutique(@PathVariable("uuid_entreprise") UUID entrepriseUuid);

    @PostMapping(value = APP_ROOT + "/utilisateurs/{uuid_utilisateur}/roles",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or (hasRole('ADMIN_BOUTIQUE') " +
            "and @securityService.canAssignRoles(#uuid_utilisateur, #entrepriseUuid))")
    public ResponseEntity<Void> assignRolesToUser (
            @PathVariable  UUID utilisateurUuid ,
            @RequestParam Set<UUID> roleUuids,
            @RequestParam(required = false) UUID entrepriseUuid,
            @RequestParam UUID assignedByUtilisateurUuid
    );

    @GetMapping(
            value = APP_ROOT + "/utilisateurs/{uuid_utilisateur}/permissions",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or #uuid_utilisateur == principal.id")
    public ResponseEntity<Set<String>> getUserPermissions(@PathVariable("uuid_utilisateur") UUID utilisateurUuid);
}
