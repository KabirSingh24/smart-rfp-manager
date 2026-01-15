package com.aerchain.rfp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "proposal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rfpId;
    private Long vendorId;

    @Column(columnDefinition = "TEXT")
    private String extractedProposal;

    private Double score;
}
