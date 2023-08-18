package com.dixmillescodeurs.poo.hello.service;

import com.dixmillescodeurs.poo.hello.model.dto.VoyageDto;
import com.dixmillescodeurs.poo.hello.model.entities.Voyage;
import com.dixmillescodeurs.poo.hello.repository.VoyageRepository;
import com.dixmillescodeurs.poo.hello.service.mapper.VoyageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoyageService {
    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private VoyageMapper voyageMapper;

    public List<VoyageDto> listVoyages(){
        /* Ancienne méthode
        List<Voyage> voyages = voyageRepository.findAll();
        List<VoyageDto> voyageDtos = new ArrayList<>();
        for (Voyage voyage:voyages){
            voyageDtos.add(voyageMapper.toDto(voyage));
        }
        return voyageRepository.findAll().stream().map(voyage -> voyageMapper.toDto(voyage)).collect(Collectors.toList());*/

        return voyageRepository.findAll().stream().map(voyageMapper::toDto).collect(Collectors.toList());
    }

    public Long ajouterVoyage(VoyageDto voyageDto){
        // Utilisation de Refactor > Extract comme best practice
        checkNomAlreadyUsed(voyageDto);
        return voyageRepository.save(voyageMapper.toEntity(voyageDto)).getId();
    }

    private void checkNomAlreadyUsed(VoyageDto voyageDto) {
        if (voyageRepository.existsByNomIgnoreCase(voyageDto.getNom())){
            throw new RuntimeException("Code 5268 : Il existe déjà un voyage avec ce nom");
        }
    }

    public boolean supprimerVoyage(String nomVoyage){
        //Recherche si le voyage existe
        // Interet de optional - permet de capturer les exceptions
        Voyage voyage = voyageRepository.findVoyageByNom(nomVoyage)
                .orElseThrow(()->new RuntimeException("Code 256 : le voyage que vous voulez supprimer n'existe pas"));

        voyageRepository.deleteById(voyage.getId());

        return true;
    }

    public boolean modifierVoyage(VoyageDto voyageDto){
        // Interet de optional - permet de capturer les exceptions
        Voyage voyage = voyageRepository.findVoyageByNom(voyageDto.getNom())
                .orElseThrow(()->new RuntimeException("Code 257 : le voyage que vous voulez modifier n'existe pas"));

        //Rassemble tout ce qu'il a envoyé à modifier dans voyageDto, le passe a l'entite voyage
        voyageMapper.copy(voyageDto, voyage);

        // Sauvegarder la modification en base de données
        voyageRepository.save(voyage);

        return true;
    }

    public VoyageDto recupererVoyage(long id) {
        return voyageMapper.toDto(voyageRepository.findById(id).get());
    }
}
