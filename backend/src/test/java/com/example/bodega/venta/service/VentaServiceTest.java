package com.example.bodega.venta.service;

import com.example.bodega.common.exception.ConflictException;
import com.example.bodega.inventario.service.InventarioService;
import com.example.bodega.producto.entity.Producto;
import com.example.bodega.producto.repository.ProductoRepository;
import com.example.bodega.usuario.entity.Usuario;
import com.example.bodega.venta.dto.*;
import com.example.bodega.venta.enums.MetodoPago;
import com.example.bodega.venta.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {
    private final VentaRepository ventas = mock(VentaRepository.class);
    private final ProductoRepository productos = mock(ProductoRepository.class);
    private final InventarioService inventario = mock(InventarioService.class);
    private final VentaService service = new VentaService(ventas, productos, inventario);

    @Test void rechazaVentaConStockInsuficiente() {
        Producto producto = producto(1L, 2);
        when(ventas.findByIdempotencyKey("key-1")).thenReturn(java.util.Optional.empty());
        when(productos.findAllByIdForUpdate(anyCollection())).thenReturn(List.of(producto));
        VentaRequest request = new VentaRequest(MetodoPago.EFECTIVO, List.of(new DetalleVentaRequest(1L, 3)));
        assertThrows(ConflictException.class, () -> service.crear(request, "key-1", 10L));
        verify(ventas, never()).save(any());
        verify(inventario, never()).apply(any(), any(), anyInt(), anyBoolean(), anyString(), any(), any());
    }

    @Test void calculaTotalEnBackendYRegistraMovimiento() {
        Producto producto = producto(1L, 5);
        Usuario usuario = new Usuario(); usuario.setId(10L); usuario.setUsername("cajero");
        when(ventas.findByIdempotencyKey("key-2")).thenReturn(java.util.Optional.empty());
        when(productos.findAllByIdForUpdate(anyCollection())).thenReturn(List.of(producto));
        when(inventario.usuario(10L)).thenReturn(usuario);
        VentaResponse response = service.crear(new VentaRequest(MetodoPago.YAPE, List.of(new DetalleVentaRequest(1L, 2))), "key-2", 10L);
        assertEquals(new BigDecimal("7.00"), response.total());
        verify(ventas).save(any());
        verify(inventario).apply(eq(producto), any(), eq(2), eq(false), contains("Venta"), eq(usuario), any());
    }

    private Producto producto(Long id, int stock) { Producto p = new Producto(); p.setId(id); p.setNombre("Producto"); p.setActivo(true); p.setStockActual(stock); p.setPrecioVenta(new BigDecimal("3.50")); return p; }
}
