import axios from 'axios'

const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api', headers: { 'Content-Type': 'application/json' } })

api.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('bodega_token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status
    if (status === 401) window.dispatchEvent(new Event('bodega:unauthorized'))
    const data = error.response?.data
    error.userMessage = data?.message || (status === 403 ? 'No tiene permisos para esta operacion.' : status === 404 ? 'No se encontro el recurso solicitado.' : 'No se pudo completar la operacion.')
    return Promise.reject(error)
  },
)

export const authApi = { login: (body) => api.post('/auth/login', body), logout: () => api.post('/auth/logout') }
export const productApi = { list: (params) => api.get('/productos', { params }), barcode: (code) => api.get(`/productos/barcode/${encodeURIComponent(code)}`), create: (body) => api.post('/productos', body), update: (id, body) => api.put(`/productos/${id}`, body), state: (id, activo) => api.patch(`/productos/${id}/estado`, { activo }) }
export const categoryApi = { list: () => api.get('/categorias'), create: (body) => api.post('/categorias', body), update: (id, body) => api.put(`/categorias/${id}`, body), state: (id, activo) => api.patch(`/categorias/${id}/estado`, { activo }) }
export const inventoryApi = { list: (params) => api.get('/inventario/movimientos', { params }), create: (body) => api.post('/inventario/movimientos', body), low: () => api.get('/alertas/stock-bajo'), out: () => api.get('/alertas/agotados'), available: () => api.get('/alertas/productos-disponibles') }
export const saleApi = { list: (params) => api.get('/ventas', { params }), get: (id) => api.get(`/ventas/${id}`), create: (body, key) => api.post('/ventas', body, { headers: { 'Idempotency-Key': key } }), cancel: (id, body) => api.post(`/ventas/${id}/anulacion`, body) }
export const reportApi = { sales: (params) => api.get('/reportes/ventas', { params }), best: (params) => api.get('/reportes/productos-mas-vendidos', { params }), value: () => api.get('/reportes/valor-inventario') }
export const userApi = { list: () => api.get('/usuarios'), create: (body) => api.post('/usuarios', body), update: (id, body) => api.put(`/usuarios/${id}`, body), state: (id, activo) => api.patch(`/usuarios/${id}/estado`, { activo }) }
export default api
