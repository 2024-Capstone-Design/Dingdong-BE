package com.seoultech.capstone.domain.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    @GetMapping ("")
    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }

}
