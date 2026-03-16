package com.bintoufha.gestionStocks.model.auth;

import com.bintoufha.gestionStocks.model.Utilisateurs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExtendedUser implements UserDetails {

    private UUID uuid;
    private UUID idEntreprise;

    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public ExtendedUser(
            String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities,
            UUID uuid, UUID idEntreprise) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.uuid = uuid;
        this.idEntreprise = idEntreprise;
    }

    /**
     * Factory method pour construire l’utilisateur enrichi
     */
    public static ExtendedUser create(Utilisateurs user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> {
                    // Ajouter le rôle
                    var roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getNomRole());
                    // Ajouter les permissions
                    var permissionAuthorities = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                            .collect(Collectors.toList());
                    permissionAuthorities.add(roleAuthority);
                    return permissionAuthorities.stream();
                }).collect(Collectors.toList());

        return new ExtendedUser(
                user.getEmail(),
                user.getNomPrenomUtilisateurs(),
                user.getPwd(),
                authorities,
                user.getUuid(),
                user.getEntreprise().getUuid() // ⚡ Entreprise pour JWT
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
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


    public boolean hasRole(String roleName) {
        return getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + roleName));
    }

}



