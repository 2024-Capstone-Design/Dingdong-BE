package com.seoultech.capstone.domain.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<OrganizationResponse> getOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private OrganizationResponse convertToResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .registeredAt(organization.getRegisteredAt())
                .type(organization.getType())
                .contactInfo(organization.getContactInfo())
                .adminInfo(organization.getAdminInfo())
                .build();
    }
}
