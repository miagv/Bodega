package com.example.bodega.venta.entity;

import com.example.bodega.usuario.entity.Usuario;
import com.example.bodega.venta.enums.EstadoVenta;
import com.example.bodega.venta.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "ventas") @Getter @Setter @NoArgsConstructor
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 40) private String numero;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "usuario_id") private Usuario usuario;
    @Column(nullable = false) private Instant fecha;
    @Enumerated(EnumType.STRING) @Column(name = "metodo_pago", nullable = false) private MetodoPago metodoPago;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EstadoVenta estado;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal total;
    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100) private String idempotencyKey;
    @Column(name = "anulada_at") private Instant anuladaAt;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "anulada_por_id") private Usuario anuladaPor;
    @Column(name = "motivo_anulacion", length = 300) private String motivoAnulacion;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true) private List<DetalleVenta> detalles = new ArrayList<>();
    public void addDetalle(DetalleVenta detalle) { detalles.add(detalle); detalle.setVenta(this); }
}
