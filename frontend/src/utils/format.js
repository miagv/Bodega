export const money = (value) => new Intl.NumberFormat('es-PE', { style: 'currency', currency: 'PEN' }).format(value ?? 0)
export const dateTime = (value) => value ? new Intl.DateTimeFormat('es-PE', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(value)) : '—'
export const today = () => new Date().toISOString().slice(0, 10)
export const uuid = () => crypto.randomUUID()
