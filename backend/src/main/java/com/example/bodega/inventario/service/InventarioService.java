package com.example.bodega.inventario.service;
import com.example.bodega.common.exception.*;
import com.example.bodega.inventario.dto.*;
import com.example.bodega.inventario.entity.MovimientoInventario;
import com.example.bodega.inventario.enums.TipoMovimiento;
import com.example.bodega.inventario.repository.MovimientoInventarioRepository;
import com.example.bodega.producto.entity.Producto;
import com.example.bodega.producto.repository.ProductoRepository;
import com.example.bodega.usuario.entity.Usuario;
import com.example.bodega.usuario.repository.UsuarioRepository;
import com.example.bodega.venta.entity.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class InventarioService {
    private final ProductoRepository productos; private final UsuarioRepository usuarios; private final MovimientoInventarioRepository movimientos;
    @Transactional public MovimientoResponse registrar(MovimientoRequest r, Long usuarioId) {
        if (r.tipo() == TipoMovimiento.VENTA || r.tipo() == TipoMovimiento.ANULACION_VENTA) throw new BusinessException("Este tipo de movimiento solo puede ser creado por una venta.");
        Producto p = productos.findAllByIdForUpdate(java.util.List.of(r.productoId())).stream().findFirst().orElseThrow(() -> new NotFoundException("Producto no encontrado."));
        boolean incrementa = r.tipo() == TipoMovimiento.ENTRADA || (r.tipo() == TipoMovimiento.AJUSTE && Boolean.TRUE.equals(r.incrementar()));
        if (r.tipo() == TipoMovimiento.AJUSTE && r.incrementar() == null) throw new BusinessException("Indique si el ajuste incrementa o reduce el stock.");
        return response(apply(p, r.tipo(), r.cantidad(), incrementa, r.motivo(), usuario(usuarioId), null));
    }
    @Transactional(readOnly = true) public org.springframework.data.domain.Page<MovimientoResponse> listar(Long productoId, TipoMovimiento tipo, java.time.LocalDate desde, java.time.LocalDate hasta, org.springframework.data.domain.Pageable pageable) {
        if (desde != null && hasta != null && hasta.isBefore(desde)) throw new BusinessException("Rango de fechas invalido.");
        java.time.Instant inicio = desde == null ? null : desde.atStartOfDay(java.time.ZoneOffset.UTC).toInstant();
        java.time.Instant fin = hasta == null ? null : hasta.plusDays(1).atStartOfDay(java.time.ZoneOffset.UTC).toInstant();
        return movimientos.search(productoId, tipo, inicio, fin, pageable).map(this::response);
    }
    public MovimientoInventario apply(Producto p, TipoMovimiento tipo, int cantidad, boolean incrementa, String motivo, Usuario usuario, Venta venta) {
        int anterior = p.getStockActual(); int posterior = incrementa ? anterior + cantidad : anterior - cantidad;
        if (posterior < 0) throw new ConflictException("Stock insuficiente para el producto: " + p.getNombre());
        p.setStockActual(posterior);
        MovimientoInventario m = new MovimientoInventario(); m.setProducto(p); m.setTipo(tipo); m.setCantidad(cantidad); m.setStockAnterior(anterior); m.setStockPosterior(posterior); m.setMotivo(motivo); m.setUsuario(usuario); m.setVenta(venta);
        return movimientos.save(m);
    }
    public Usuario usuario(Long id) { return usuarios.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado.")); }
    private MovimientoResponse response(MovimientoInventario m) { return new MovimientoResponse(m.getId(),m.getProducto().getId(),m.getTipo(),m.getCantidad(),m.getStockAnterior(),m.getStockPosterior(),m.getMotivo(),m.getUsuario().getUsername(),m.getFecha()); }
}
