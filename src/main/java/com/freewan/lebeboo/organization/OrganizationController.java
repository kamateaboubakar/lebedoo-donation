package com.freewan.lebeboo.organization;

import com.freewan.lebeboo.common.http.ApiResponseCode;
import com.freewan.lebeboo.Route;
import com.freewan.lebeboo.account.AccountClient;
import com.freewan.lebeboo.common.http.ApiResponse;
import com.freewan.lebeboo.exception.ApplicationException;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.freewan.lebeboo.Route.ORGANIZATION;
import static com.freewan.lebeboo.Route.ROOT;
import static com.freewan.lebeboo.Route.V1_URI;

@RestController
@RequestMapping(ROOT + V1_URI + ORGANIZATION)
@RequiredArgsConstructor
@Validated
public class OrganizationController {

    private final OrganizationService organizationService;
    private final AccountClient client;
    private final OrganizationMapper mapper;

    @Operation(summary = "Get all organizations")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrganizationDto> getAllCompanies() {
        return mapper.toDtos(organizationService.findAll());
    }

    @Operation(summary = "Get all organizations by customer account id")
    @GetMapping(value = Route.CUSTOMER + "/{customerAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrganizationDto> getAllCompaniesByCustomerAccountId(@PathVariable @NotBlank String customerAccountId) {
        return mapper.toDtos(organizationService.findAllByCustomerAccountId(customerAccountId));
    }

    @Operation(summary = "Get an organization by id")
    @GetMapping(value = "/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto getOrganizationById(@PathVariable @NotNull Long organizationId) {
        return mapper.toDto(organizationService.findById(organizationId));
    }

    @Operation(summary = "Add an organization")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto addNewOrganization(@RequestBody @Valid OrganizationDto dto) {
        if (dto.getCustomerAccountId() != null && !dto.getCustomerAccountId().isBlank()) {
            try {
                client.getAccountById(dto.getCustomerAccountId());
            } catch (FeignException.FeignClientException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        Organization organization = mapper.toEntity(dto);
        return mapper.toDto(organizationService.save(organization));
    }


    @Operation(summary = "Update an organization")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto updateOrganization(@RequestBody @Valid OrganizationDto dto) {
        Organization sector = organizationService.findById(dto.getId());
        if (dto.getCustomerAccountId() != null && !dto.getCustomerAccountId().isBlank()
                && !dto.getCustomerAccountId().equals(sector.getCustomerAccountId())) {
            try {
                client.getAccountById(dto.getCustomerAccountId());
            } catch (FeignException.FeignClientException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        sector = mapper.partialUpdate(dto, sector);
        return mapper.toDto(organizationService.save(sector));
    }

    @Operation(summary = "Delete organization by id")
    @DeleteMapping(value = "/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteOrganizationById(@PathVariable @NotNull Long organizationId) {
        organizationService.deleteById(organizationId);
        return new ApiResponse(ApiResponseCode.SUCCESS, "Organization deleted with success.");
    }
}
