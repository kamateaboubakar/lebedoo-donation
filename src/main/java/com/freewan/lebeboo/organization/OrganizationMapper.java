package com.freewan.lebeboo.organization;

import com.freewan.lebeboo.common.storage.FileStorageUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "verified", ignore = true)
    Organization toEntity(OrganizationDto organizationDto);

    @Mapping(source = "logo", target = "logo")
    @Mapping(source = "logo", target = "logoUrl", qualifiedByName = "fileNameToPath")
    OrganizationDto toDto(Organization organization);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Organization partialUpdate(OrganizationDto organizationDto, @MappingTarget Organization organization);

    List<OrganizationDto> toDtos(List<Organization> all);

    @Named("fileNameToPath")
    static String fileNameToPath(String filename) {
        if (filename == null || filename.isBlank()) return "";
        return FileStorageUtils.getFileUrlByFilename(filename);
    }
}