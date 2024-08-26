package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "csvc_user")
public class CsvcUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "auth")
    private String auth;
    @Column(name = "code_user")
    private String codeUser;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "path_avatar")
    private String pathAvatar;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "delete_at")
    private String deleteAt;
    @Column(name = "first_access")
    private String firstAccess;
    @Column(name = "last_access")
    private String lastAccess;
    @Column(name = "last_login")
    private String lastLogin;
    @Column(name = "current_login")
    private String currentLogin;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "is_actived")
    private Integer isActived;

}
