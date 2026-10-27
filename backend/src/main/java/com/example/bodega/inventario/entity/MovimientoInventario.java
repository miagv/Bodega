package com.example.bodega.inventario.entity;

import com.example.bodega.inventario.enums.TipoMovimiento;
import com.example.bodega.producto.entity.Producto;
import com.example.bodega.usuario.entity.Usuario;
import com.example.bodega.venta.entity.Venta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity @Table(name = "movimientos_inventario") @Getter @Setter @NoArgsConstructor
public class MovimientoInventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "producto_id") private Producto producto;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "venta_id") private Venta venta;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TipoMovimiento tipo;
    @Column(nullable = false) private int cantidad;
    @Column(name = "stock_anterior", nullable = false) private int stockAnterior;
    @Column(name = "stock_posterior", nullable = false) private int stockPosterior;
    @Column(nullable = false, length = 300) private String motivo;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "usuario_id") private Usuario usuario;
    @CreationTimestamp @Column(nullable = false, updatable = false) private Instant fecha;
}
