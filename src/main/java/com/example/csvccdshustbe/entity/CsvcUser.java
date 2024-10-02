package com.example.csvccdshustbe.entity;

import com.example.csvccdshustbe.utility.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "csvc_user")
public class CsvcUser implements OAuth2User,UserDetails {

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

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> role;
    private Integer idDepartmentCurrent;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(this.role));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.getIsActived().equals(Constants.ACCOUNT_IS_LOCK)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Capabilities> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add("ROLE_" + role.getTitle());
            collection.addAll(role.getCapabilities());
        }
        for (Capabilities item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    @Override
    public String getName() {
        return this.userName;
    }
}
