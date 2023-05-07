package com.springrest.linkcut.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long linkId;
    @Column(name = "short_link",nullable = false)
    String shortLink;
    @Column(name = "long_link",nullable = false)
    String longLink;
}
