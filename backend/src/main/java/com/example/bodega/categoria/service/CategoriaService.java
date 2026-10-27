package com.example.bodega.categoria.service;
import com.example.bodega.categoria.dto.*;
import com.example.bodega.categoria.entity.Categoria;
import com.example.bodega.categoria.mapper.CategoriaMapper;
import com.example.bodega.categoria.repository.CategoriaRepository;
import com.example.bodega.common.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class CategoriaService {
    private final CategoriaRepository repository;
    public List<CategoriaResponse> listar() { return repository.findAll().stream().map(CategoriaMapper::toResponse).toList(); }
    public CategoriaResponse obtener(Long id) { return CategoriaMapper.toResponse(entity(id)); }
    @Transactional public CategoriaResponse crear(CategoriaRequest r) { if (repository.existsByNombreIgnoreCase(r.nombre().trim())) throw new ConflictException("Ya existe una categoria con ese nombre."); Categoria c=new Categoria(); apply(c,r); return CategoriaMapper.toResponse(repository.save(c)); }
    @Transactional public CategoriaResponse actualizar(Long id, CategoriaRequest r) { Categoria c=entity(id); if (!c.getNombre().equalsIgnoreCase(r.nombre().trim()) && repository.existsByNombreIgnoreCase(r.nombre().trim())) throw new ConflictException("Ya existe una categoria con ese nombre."); apply(c,r); return CategoriaMapper.toResponse(c); }
    @Transactional public void desactivar(Long id) { entity(id).setActiva(false); }
    @Transactional public CategoriaResponse cambiarEstado(Long id, boolean activa) { Categoria c = entity(id); c.setActiva(activa); return CategoriaMapper.toResponse(c); }
    private Categoria entity(Long id) { return repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria no encontrada.")); }
    private void apply(Categoria c, CategoriaRequest r) { c.setNombre(r.nombre().trim()); c.setDescripcion(r.descripcion()); }
}
