package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_refresh_token")
    private Integer idRefreshToken;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "token")
    private String token;
    @Column(name = "expire_date")
    private String expireDate;
    @Column(name = "revoked")
    private Boolean revoked;
}
