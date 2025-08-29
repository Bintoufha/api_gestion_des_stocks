package com.bintoufha.gestionStocks.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class ExtendedUser extends User {
    @Getter
    @Setter
    private UUID idEntreprise;

    public ExtendedUser(String username, String password,
          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities,
                        UUID idEntreprise) {
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}
