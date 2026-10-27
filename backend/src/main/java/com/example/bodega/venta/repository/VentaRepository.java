package com.example.bodega.venta.repository;
import com.example.bodega.venta.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;
import java.time.Instant;
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByIdempotencyKey(String key);
    List<Venta> findByFechaBetween(Instant desde, Instant hasta);
    @Query("select v from Venta v where (:desde is null or v.fecha >= :desde) and (:hasta is null or v.fecha < :hasta) and (:estado is null or v.estado = :estado) and (:usuarioId is null or v.usuario.id = :usuarioId)")
    org.springframework.data.domain.Page<Venta> search(@org.springframework.data.repository.query.Param("desde") Instant desde, @org.springframework.data.repository.query.Param("hasta") Instant hasta, @org.springframework.data.repository.query.Param("estado") com.example.bodega.venta.enums.EstadoVenta estado, @org.springframework.data.repository.query.Param("usuarioId") Long usuarioId, org.springframework.data.domain.Pageable pageable);
}
