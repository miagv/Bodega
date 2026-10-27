package com.example.bodega.producto.service;

import com.example.bodega.categoria.repository.CategoriaRepository;
import com.example.bodega.common.exception.*;
import com.example.bodega.inventario.service.InventarioService;
import com.example.bodega.producto.dto.ProductoRequest;
import com.example.bodega.producto.enums.UnidadMedida;
import com.example.bodega.producto.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    private final ProductoRepository productos = mock(ProductoRepository.class);
    private final ProductoService service = new ProductoService(productos, mock(CategoriaRepository.class), mock(InventarioService.class));

    @Test void rechazaPrecioVentaMenorQueCompra() {
        ProductoRequest request = new ProductoRequest("Arroz", null, null, 1L, new BigDecimal("10.00"), new BigDecimal("9.00"), 0, 1, UnidadMedida.UNIDAD);
        assertThrows(BusinessException.class, () -> service.crear(request, 1L));
        verifyNoInteractions(productos);
    }

    @Test void rechazaCodigoDeBarrasDuplicado() {
        when(productos.existsByCodigoBarras("00123")).thenReturn(true);
        ProductoRequest request = new ProductoRequest("Arroz", null, "00123", 1L, new BigDecimal("10.00"), new BigDecimal("12.00"), 0, 1, UnidadMedida.UNIDAD);
        assertThrows(ConflictException.class, () -> service.crear(request, 1L));
        verify(productos).existsByCodigoBarras("00123");
    }
}
