import { useEffect, useState } from 'react'
import { authApi } from '../services/api'
import { AuthContext } from './AuthContextValue'
const key = 'bodega_session'

export function AuthProvider({ children }) {
  const [session, setSession] = useState(() => { try { return JSON.parse(sessionStorage.getItem(key)) } catch { return null } })
  const logout = async () => { try { await authApi.logout() } catch { /* Local logout is still required for stateless JWT. */ } sessionStorage.removeItem(key); sessionStorage.removeItem('bodega_token'); setSession(null) }
  const login = async (credentials) => { const { data } = await authApi.login(credentials); const next = { ...data, expiresAt: Date.now() + data.expiresInSeconds * 1000 }; sessionStorage.setItem('bodega_token', data.token); sessionStorage.setItem(key, JSON.stringify(next)); setSession(next); return next }
  useEffect(() => { const expired = () => { if (session && session.expiresAt <= Date.now()) logout() }; expired(); const timer = setInterval(expired, 30_000); window.addEventListener('bodega:unauthorized', logout); return () => { clearInterval(timer); window.removeEventListener('bodega:unauthorized', logout) } }, [session])
  return <AuthContext.Provider value={{ session, login, logout, isAdmin: session?.rol === 'ADMIN' }}>{children}</AuthContext.Provider>
}
