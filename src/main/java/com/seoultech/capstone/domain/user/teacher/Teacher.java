package com.seoultech.capstone.domain.user.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seoultech.capstone.domain.organization.Organization;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "service_usage", length = 255)
    private String serviceUsage;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "profile_url", length = 255)
    private String profileUrl;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public TeacherResponse toTeacherResponse() {
        TeacherResponse response = new TeacherResponse();
        response.setId(this.id);
        response.setName(this.name);
        response.setPhoneNumber(this.phoneNumber);
        response.setOrganization(this.organization.toOrganizationResponse());

        response.setProfileUrl(this.profileUrl);
        response.setLastAccessedAt(this.lastAccessedAt);
        return response;
    }

    public void updatePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

    public void updateProfileUrl(String updateProfileUrl) {
        this.profileUrl = updateProfileUrl;
    }

}

