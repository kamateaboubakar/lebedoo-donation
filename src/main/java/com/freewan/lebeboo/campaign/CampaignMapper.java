package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.category.CategoryMapper;
import com.freewan.lebeboo.common.storage.FileStorageUtils;
import com.freewan.lebeboo.organization.OrganizationMapper;
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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryMapper.class, OrganizationMapper.class})
public interface CampaignMapper {

    @Mapping(source = "image", target = "image")
    @Mapping(source = "image", target = "imageUrl", qualifiedByName = "imageToUrl")
    CampaignDto toDto(Campaign campaign);

    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "id", ignore = true)
    Campaign fromRequestToEntity(CampaignRequest campaignRequest);

    @InheritConfiguration(name = "fromRequestToEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Campaign partialUpdateFromRequest(CampaignRequest campaignRequest, @MappingTarget Campaign campaign);

    List<CampaignDto> toDtos(List<Campaign> all);
    @Named("imageToUrl")
    static String fileNameToPath(String filename) {
        if (filename == null || filename.isBlank()) return "";
        return FileStorageUtils.getFileUrlByFilename(filename);
    }
}