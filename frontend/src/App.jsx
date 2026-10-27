import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext.jsx'
import { useAuth } from './hooks/useAuth'
import Layout from './components/layout/Layout'
import LoginPage from './pages/LoginPage'
import DashboardPage from './pages/DashboardPage'
import ProductsPage from './pages/ProductsPage'
import CategoriesPage from './pages/CategoriesPage'
import InventoryPage from './pages/InventoryPage'
import SalesPage from './pages/SalesPage'
import NewSalePage from './pages/NewSalePage'
import ReportsPage from './pages/ReportsPage'
import UsersPage from './pages/UsersPage'
function Guard({ children, admin = false }) { const { session, isAdmin } = useAuth(); return !session ? <Navigate to="/login" replace /> : admin && !isAdmin ? <Navigate to="/" replace /> : children }
function AppRoutes() { return <Routes><Route path="/login" element={<LoginPage/>}/><Route element={<Guard><Layout/></Guard>}><Route path="/" element={<DashboardPage/>}/><Route path="/productos" element={<ProductsPage/>}/><Route path="/inventario" element={<Guard admin><InventoryPage/></Guard>}/><Route path="/ventas" element={<SalesPage/>}/><Route path="/ventas/nueva" element={<NewSalePage/>}/><Route path="/categorias" element={<Guard admin><CategoriesPage/></Guard>}/><Route path="/reportes" element={<Guard admin><ReportsPage/></Guard>}/><Route path="/usuarios" element={<Guard admin><UsersPage/></Guard>}/></Route><Route path="*" element={<Navigate to="/" replace/>}/></Routes> }
export default function App(){return <BrowserRouter><AuthProvider><AppRoutes/></AuthProvider></BrowserRouter>}
