package com.aerchain.rfp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rfp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rfp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String originalText;

    @Column(columnDefinition = "TEXT")
    private String structuredData;

    private Double budget;
}
