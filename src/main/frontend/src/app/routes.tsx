import React from 'react'
import { createBrowserRouter, Outlet } from 'react-router-dom'
import AppLayout from './AppLayout'
import BeersPage from '../features/beers/BeersPage'
import BeerDetailPage from '../features/beers/BeerDetailPage'
import CustomersPage from '../features/customers/CustomersPage'
import CustomerDetailPage from '../features/customers/CustomerDetailPage'
import OrdersPage from '../features/orders/OrdersPage'
import OrderDetailPage from '../features/orders/OrderDetailPage'
import ShipmentsPage from '../features/shipments/ShipmentsPage'
import ShipmentDetailPage from '../features/shipments/ShipmentDetailPage'

function Root() {
  return (
    <AppLayout>
      <Outlet />
    </AppLayout>
  )
}

export const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    children: [
      { index: true, element: <BeersPage /> },
      { path: 'beers', element: <BeersPage /> },
      { path: 'beers/:beerId', element: <BeerDetailPage /> },
      { path: 'customers', element: <CustomersPage /> },
      { path: 'customers/:customerId', element: <CustomerDetailPage /> },
      { path: 'orders', element: <OrdersPage /> },
      { path: 'orders/:orderId', element: <OrderDetailPage /> },
      { path: 'shipments', element: <ShipmentsPage /> },
      { path: 'shipments/:shipmentId', element: <ShipmentDetailPage /> },
    ],
  },
])

export default router
