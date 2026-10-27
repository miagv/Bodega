package com.example.bodega.categoria.controller;
import com.example.bodega.categoria.dto.*; import com.example.bodega.common.dto.EstadoRequest;
import com.example.bodega.categoria.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/categorias") @RequiredArgsConstructor
public class CategoriaController { private final CategoriaService service; @GetMapping public List<CategoriaResponse> listar(){return service.listar();} @GetMapping("/{id}") public CategoriaResponse obtener(@PathVariable Long id){return service.obtener(id);} @PostMapping @PreAuthorize("hasRole('ADMIN')") public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(r));} @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public CategoriaResponse actualizar(@PathVariable Long id,@Valid @RequestBody CategoriaRequest r){return service.actualizar(id,r);}@PatchMapping("/{id}/estado") @PreAuthorize("hasRole('ADMIN')") public CategoriaResponse estado(@PathVariable Long id,@Valid @RequestBody EstadoRequest r){return service.cambiarEstado(id,r.activo());}@PatchMapping("/{id}/desactivar") @PreAuthorize("hasRole('ADMIN')") @ResponseStatus(HttpStatus.NO_CONTENT) public void desactivar(@PathVariable Long id){service.desactivar(id);} }
