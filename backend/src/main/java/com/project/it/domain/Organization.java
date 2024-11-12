
package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_organization")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name="member_mno")
    private Long mno;

    @Column(nullable = false)
    private String teamName;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrganizationTeam> organizationTeamList = new ArrayList<>();

    public void addOrganizationTeam(OrganizationTeam organizationTeam){
        organizationTeamList.add(organizationTeam);
    }

}

