package com.freewan.lebeboo.donation;

import com.freewan.lebeboo.common.storage.FileStorageUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CustomerDetailsResolver.class})
public interface DonationMapper {
    @Mapping(source = "campaign.id", target = "campaignId")
    DonationDto toDto(Donation donation);

    List<DonationDto> toDtos(List<Donation> donations);

    @Named("fileNameToPath")
    static String fileNameToPath(String filename) {
        if (filename == null || filename.isBlank()) return "";
        return FileStorageUtils.getFileUrlByFilename(filename);
    }
}