package com.unleash.articleservice.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String title;

    private int relatedTo;

    @Column(columnDefinition="LONGTEXT")
    private String  content;

    private String image;

    private int counselorId;

    private String counselorName;

    private Timestamp uploadedOn;
}
