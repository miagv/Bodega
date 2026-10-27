package com.example.bodega.producto.entity;

import com.example.bodega.categoria.entity.Categoria;
import com.example.bodega.producto.enums.UnidadMedida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name = "productos") @Getter @Setter @NoArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String nombre;
    @Column(length = 500) private String descripcion;
    @Column(name = "codigo_barras", length = 100) private String codigoBarras;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "categoria_id") private Categoria categoria;
    @Column(name = "precio_compra", nullable = false, precision = 14, scale = 2) private BigDecimal precioCompra;
    @Column(name = "precio_venta", nullable = false, precision = 14, scale = 2) private BigDecimal precioVenta;
    @Column(name = "stock_actual", nullable = false) private int stockActual;
    @Column(name = "stock_minimo", nullable = false) private int stockMinimo;
    @Enumerated(EnumType.STRING) @Column(name = "unidad_medida", nullable = false, length = 20) private UnidadMedida unidadMedida;
    @Column(nullable = false) private boolean activo = true;
    @Version private long version;
    @CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @UpdateTimestamp @Column(name = "updated_at", nullable = false) private Instant updatedAt;
}
