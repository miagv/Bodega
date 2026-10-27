package com.example.bodega.inventario.repository;
import com.example.bodega.inventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    @Query("select m from MovimientoInventario m where (:productoId is null or m.producto.id = :productoId) and (:tipo is null or m.tipo = :tipo) and (:desde is null or m.fecha >= :desde) and (:hasta is null or m.fecha < :hasta)")
    org.springframework.data.domain.Page<MovimientoInventario> search(@Param("productoId") Long productoId, @Param("tipo") com.example.bodega.inventario.enums.TipoMovimiento tipo, @Param("desde") Instant desde, @Param("hasta") Instant hasta, org.springframework.data.domain.Pageable pageable);
}
