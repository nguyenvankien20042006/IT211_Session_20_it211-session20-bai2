package com.example.bai2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artworks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean isPublished;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
