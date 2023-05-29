package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.Route;
import com.freewan.lebeboo.account.AccountClient;
import com.freewan.lebeboo.category.CategoryService;
import com.freewan.lebeboo.common.http.ApiResponse;
import com.freewan.lebeboo.common.http.ApiResponseCode;
import com.freewan.lebeboo.common.storage.FileSystemStorageService;
import com.freewan.lebeboo.donation.Donation;
import com.freewan.lebeboo.donation.DonationDto;
import com.freewan.lebeboo.donation.DonationMapper;
import com.freewan.lebeboo.donation.DonationRepository;
import com.freewan.lebeboo.donation.DonationRequest;
import com.freewan.lebeboo.exception.ApplicationException;
import com.freewan.lebeboo.organization.OrganizationService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.freewan.lebeboo.Route.CAMPAIGN;
import static com.freewan.lebeboo.Route.DONATION;
import static com.freewan.lebeboo.Route.ROOT;
import static com.freewan.lebeboo.Route.V1_URI;

@RestController
@RequestMapping(ROOT + V1_URI + CAMPAIGN)
@RequiredArgsConstructor
@Transactional
@Validated
public class CampaignController {

    private final CampaignService campaignService;
    private final DonationRepository donationRepository;
    private final CampaignMapper mapper;
    private final DonationMapper donationMapper;
    private final AccountClient accountClient;
    private final OrganizationService organizationService;
    private final CategoryService categoryService;
    private final FileSystemStorageService fileSystemStorageService;

    @Operation(summary = "Get all campaigns")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CampaignDto> getAllCampaign() {
        return mapper.toDtos(campaignService.findAll());
    }

    @Operation(summary = "Get all campaigns created by customer account id")
    @GetMapping(value = Route.CUSTOMER + "/{customerAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CampaignDto> getAllCampaignByCustomerAccountId(@PathVariable @NotBlank String customerAccountId) {
        return mapper.toDtos(campaignService.findAllByCustomerAccountId(customerAccountId));
    }

    @Operation(summary = "Get campaign by id")
    @GetMapping(value = "/{campaignId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CampaignDto getCampaignById(@PathVariable Long campaignId) {
        return mapper.toDto(campaignService.findById(campaignId));
    }

    @Operation(summary = "Get all donation for a campaign")
    @GetMapping(value = "/{campaignId}" + DONATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DonationDto> getAllCampaignApplication(@PathVariable @NotNull Long campaignId) {
        Campaign campaign = campaignService.findById(campaignId);
        return donationMapper.toDtos(
                donationRepository.findAllByCampaign(campaign));
    }

    @Operation(summary = "Get all donations for a campaign by customer account id")
    @GetMapping(value = DONATION + "/customers" + "/{customerAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DonationDto> getAllCampaignApplicationByAccountId(@PathVariable @NotBlank String customerAccountId) {
        return donationMapper.toDtos(
                donationRepository.findAllByCustomerAccountId(customerAccountId));
    }

    @Operation(summary = "Make a donation to a campaign by id")
    @PostMapping(value = "/{campaignId}/donate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse applyToCampaign(@PathVariable @NotNull Long campaignId, @RequestBody @Valid DonationRequest request) {
        Campaign campaign = campaignService.findById(campaignId);
        // Verify account id.
        try {
            accountClient.getAccountById(request.getCustomerAccountId());
        } catch (FeignException e) {
            throw new ApplicationException(e.getMessage());
        }
        Donation donation = new Donation();
        donation.setCampaign(campaign);
        donation.setCustomerAccountId(request.getCustomerAccountId());
        donation.setAmount(request.getAmount());
        donationRepository.save(donation);
        return new ApiResponse(ApiResponseCode.SUCCESS, "Successfully donate to campaign.");
    }

    @Operation(summary = "Add a campaign")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addNewCampaign(@RequestBody @Valid CampaignRequest request) {
        validateRequest(request);
        Campaign campaign = mapper.fromRequestToEntity(request);
        campaignService.save(campaign);
        return new ResponseEntity<>(new ApiResponse(ApiResponseCode.SUCCESS, "Campaign create with success."), HttpStatus.CREATED);
    }


    @Operation(summary = "Update a campaign")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateCampaign(@RequestBody @Valid CampaignRequest request) {
        if (request.getId() == null) {
            throw new ApplicationException("Campaign id must not be null");
        }
        Campaign campaign = campaignService.findById(request.getId());
        validateRequest(request);
        campaign = mapper.partialUpdateFromRequest(request, campaign);
        campaignService.save(campaign);
        return ResponseEntity.ok(new ApiResponse(ApiResponseCode.SUCCESS, "Campaign create with success."));
    }

    @Operation(summary = "Delete a campaign by id")
    @DeleteMapping(value = "/{campaignId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = IOException.class)
    public ApiResponse deleteCampaignById(@PathVariable @NotNull Long campaignId) throws IOException {
        Campaign campaign = campaignService.findById(campaignId);
        campaignService.delete(campaign);
        fileSystemStorageService.delete(campaign.getImage());
        return new ApiResponse(ApiResponseCode.SUCCESS, "Campaign deleted with success.");
    }

    void validateRequest(CampaignRequest request) {
        categoryService.findById(request.getCategoryId());
        organizationService.findById(request.getOrganizationId());
    }
}
