package com.springrest.linkcut.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "userLink")
public class UserLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long user_id;
    String username;
    String short_link;
    Long longLink;
}
