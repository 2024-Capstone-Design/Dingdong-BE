package com.seoultech.capstone.domain.organization;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(length = 100)
    private String type;

    @Column(name = "contact_info", length = 255)
    private String contactInfo;

    @Column(name = "admin_info", length = 255)
    private String adminInfo;

    public OrganizationResponse toOrganizationResponse() {
        return OrganizationResponse.builder()
                .id(this.id)
                .name(this.name)
                .registeredAt(this.registeredAt)
                .type(this.type)
                .build();
    }

}

