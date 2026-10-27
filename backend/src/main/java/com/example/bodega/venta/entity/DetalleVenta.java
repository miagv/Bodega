package com.example.bodega.venta.entity;

import com.example.bodega.producto.entity.Producto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity @Table(name = "detalles_venta") @Getter @Setter @NoArgsConstructor
public class DetalleVenta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "venta_id") private Venta venta;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "producto_id") private Producto producto;
    @Column(nullable = false) private int cantidad;
    @Column(name = "precio_unitario", nullable = false, precision = 14, scale = 2) private BigDecimal precioUnitario;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal subtotal;
}
