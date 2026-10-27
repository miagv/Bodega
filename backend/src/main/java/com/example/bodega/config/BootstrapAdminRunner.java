package com.example.bodega.config;
import com.example.bodega.usuario.entity.Usuario;
import com.example.bodega.usuario.enums.Role;
import com.example.bodega.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration @RequiredArgsConstructor
public class BootstrapAdminRunner {
    private final UsuarioRepository usuarios; private final PasswordEncoder encoder;
    @Bean ApplicationRunner bootstrapAdmin(@Value("${app.bootstrap-admin.username}") String username, @Value("${app.bootstrap-admin.password}") String password) {
        return args -> { if (!username.isBlank() && !password.isBlank() && !usuarios.existsByRolAndActivoTrue(Role.ADMIN)) { Usuario admin = new Usuario(); admin.setUsername(username.trim()); admin.setNombre("Administrador inicial"); admin.setPasswordHash(encoder.encode(password)); admin.setRol(Role.ADMIN); admin.setActivo(true); usuarios.save(admin); } };
    }
}
