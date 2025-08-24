package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import com.bintoufha.gestionStocks.repository.LigneCommandeClientsRepository;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import com.bintoufha.gestionStocks.services.CommandeFournisseurService;
import com.bintoufha.gestionStocks.validator.CommandClientsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final LigneCommandeClientsRepository ligneCommandeClientsRepository;
    private CommandeClientsRepository commandeClientsRepository;
    private ClientsRepository clientsRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CommandeFournisseurServiceImpl(
            CommandeClientsRepository commandeClientsRepository,
            ClientsRepository clientsRepository,
            ArticleRepository articleRepository, LigneCommandeClientsRepository ligneCommandeClientsRepository) {
        this.commandeClientsRepository = commandeClientsRepository;
        this.clientsRepository = clientsRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientsRepository = ligneCommandeClientsRepository;
    }

    @Override
    public CommandeFournisseursDto save(CommandeFournisseursDto commandeFournisseursDto) {
        return null;
    }

    @Override
    public CommandeFournisseursDto findByUUID(UUID uuid) {
        return null;
    }

    @Override
    public CommandeFournisseursDto findByReference(String reference) {
        return null;
    }

    @Override
    public List<CommandeFournisseursDto> findAll() {
        return List.of();
    }

    @Override
    public void delete(UUID uuid) {

    }
}
