package com.dixmillescodeurs.poo.hello.repository;

import com.dixmillescodeurs.poo.hello.model.entities.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Permet l'utilisation de ce repos dans service
@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    boolean existsByNomIgnoreCase(String reference);

    // Recherche d'entite - utilisation de Optional
    Optional<Voyage> findVoyageByNom(String nom);
}

