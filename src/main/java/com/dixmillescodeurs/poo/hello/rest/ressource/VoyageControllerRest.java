package com.dixmillescodeurs.poo.hello.rest.ressource;

import com.dixmillescodeurs.poo.hello.model.dto.VoyageDto;
import com.dixmillescodeurs.poo.hello.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voyage")
public class VoyageControllerRest {

    @Autowired
    private VoyageService voyageService;


    @GetMapping("/getall")
    public ResponseEntity<List<VoyageDto>> listevoyages() {
        return  ResponseEntity.ok(voyageService.listVoyages());
    }

    @PostMapping("/create")
    public ResponseEntity<Long> enregistrerVoyage(@RequestBody VoyageDto voyageDto) {

        return ResponseEntity.ok( voyageService.ajouterVoyage(voyageDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<VoyageDto> getVoyageById( @PathVariable long id) {
        return  ResponseEntity.ok(voyageService.recupererVoyage(id));
    }

    @DeleteMapping("/delete/{nom-voyage}")
    public ResponseEntity<Boolean> deleteById( @PathVariable(value = "nom-voyage") String nomVoyage) {
        return  ResponseEntity.ok(voyageService.supprimerVoyage(nomVoyage));
    }

}
