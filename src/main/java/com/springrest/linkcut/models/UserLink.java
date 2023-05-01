package com.springrest.linkcut.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "userLink")
public class UserLink implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long user_id;
    String username;
    String shortLink;
    String longLink;
}
