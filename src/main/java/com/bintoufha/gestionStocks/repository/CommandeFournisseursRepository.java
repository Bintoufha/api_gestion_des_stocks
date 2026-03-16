package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandeFournisseursRepository extends JpaRepository<CommandeFournisseurs,UUID> {

    Optional<CommandeFournisseurs> findByUuid (UUID uuid);

    List<CommandeFournisseurs> findAllByFournisseursUuid(UUID uuid);

    Optional<CommandeFournisseurs> findByReference(String reference);
}
