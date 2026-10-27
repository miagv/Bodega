package com.example.bodega.producto.repository;
import com.example.bodega.producto.entity.Producto;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);
    @Query("select p from Producto p where (:nombre is null or lower(p.nombre) like lower(concat('%', :nombre, '%'))) and (:categoriaId is null or p.categoria.id = :categoriaId) and (:activo is null or p.activo = :activo)")
    Page<Producto> search(@Param("nombre") String nombre, @Param("categoriaId") Long categoriaId, @Param("activo") Boolean activo, Pageable pageable);
    @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select p from Producto p where p.id in :ids order by p.id")
    List<Producto> findAllByIdForUpdate(@Param("ids") Collection<Long> ids);
    @Query("select p from Producto p where p.activo = true and p.stockActual <= p.stockMinimo")
    List<Producto> findStockBajo();
    List<Producto> findByActivoTrueAndStockActual(int stockActual);
    List<Producto> findByActivoTrueAndStockActualGreaterThan(int stockActual);
}
