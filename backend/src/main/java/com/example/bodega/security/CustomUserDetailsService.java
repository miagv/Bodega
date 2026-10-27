package com.example.bodega.security;
import com.example.bodega.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarios;
    @Override public UserDetails loadUserByUsername(String username) { return usuarios.findByUsername(username).map(SecurityUser::from).orElseThrow(() -> new UsernameNotFoundException("Credenciales invalidas.")); }
}
