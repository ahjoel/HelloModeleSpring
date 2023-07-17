package com.dixmillescodeurs.poo.hello.service.mapper;

import com.dixmillescodeurs.poo.hello.model.dto.VoyageDto;
import com.dixmillescodeurs.poo.hello.model.entities.Voyage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;


@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface VoyageMapper {
    Voyage toEntity(VoyageDto voyageDto);
    VoyageDto toDto(Voyage voyage);

    void copy(VoyageDto voyageDto, @MappingTarget Voyage voyage);

    // Il faut vérifier l'implémentation du toDTO avec l'annotation
    // @Component - ensuite le retirer
}
