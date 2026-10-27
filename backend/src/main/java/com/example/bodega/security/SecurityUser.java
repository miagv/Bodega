package com.example.bodega.security;
import com.example.bodega.usuario.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
public record SecurityUser(Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) implements UserDetails {
    public static SecurityUser from(Usuario u) { return new SecurityUser(u.getId(), u.getUsername(), u.getPasswordHash(), u.isActivo(), List.of(new SimpleGrantedAuthority("ROLE_" + u.getRol().name()))); }
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
}
