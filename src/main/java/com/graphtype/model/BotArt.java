package com.graphtype.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "botart")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BotArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    @Column
    private String url;

    @Column
    private String description;
}
