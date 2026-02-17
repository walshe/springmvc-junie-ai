import React from 'react'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Plus } from 'lucide-react'
import { toast } from 'sonner'
import type { BeerOrder } from '@/api/models/BeerOrder'
import type { CreateBeerOrderCommand } from '@/api/models/CreateBeerOrderCommand'
import { listOrders, createOrder } from './api/orders'
import { DataTable } from '@/components/DataTable'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Modal } from '@/components/Modal'
import { OrderForm } from './components/OrderForm'

export default function OrdersPage() {
  const [data, setData] = useState<BeerOrder[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const fetchOrders = async () => {
    setIsLoading(true)
    try {
      const orders = await listOrders()
      setData(orders)
    } catch (error) {
      console.error('Failed to fetch orders:', error)
      toast.error('Failed to load orders')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchOrders()
  }, [])

  const handleCreateOrder = async (command: CreateBeerOrderCommand) => {
    setIsSubmitting(true)
    try {
      await createOrder(command)
      toast.success('Order created successfully')
      setIsCreateModalOpen(false)
      fetchOrders()
    } catch (error) {
      console.error('Failed to create order:', error)
      toast.error('Failed to create order')
    } finally {
      setIsSubmitting(false)
    }
  }

  const columns = [
    {
      header: 'Order ID',
      accessorKey: 'id' as keyof BeerOrder,
      className: 'w-[100px]',
    },
    {
      header: 'Customer Ref',
      accessorKey: (order: BeerOrder) => (
        <Link
          to={`/orders/${order.id}`}
          className="font-medium text-primary hover:underline"
        >
          {order.customerRef || `Order #${order.id}`}
        </Link>
      ),
    },
    {
      header: 'Status',
      accessorKey: (order: BeerOrder) => {
        const status = order.status
        return (
          <Badge variant={status === 'DELIVERED' ? 'default' : 'secondary'}>
            {status}
          </Badge>
        )
      },
    },
    {
      header: 'Total Items',
      accessorKey: (order: BeerOrder) => order.beerOrderLines?.length || 0,
    },
    {
      header: 'Payment Amount',
      accessorKey: (order: BeerOrder) =>
        order.paymentAmount !== undefined
          ? `$${order.paymentAmount.toFixed(2)}`
          : '-',
      className: 'text-right',
    },
    {
      header: 'Created Date',
      accessorKey: (order: BeerOrder) =>
        order.createdDate ? new Date(order.createdDate).toLocaleDateString() : '-',
    },
  ]

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Beer Orders</h1>
          <p className="text-muted-foreground">
            Manage and track customer beer orders.
          </p>
        </div>
        <Button onClick={() => setIsCreateModalOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Create Order
        </Button>
      </div>

      <DataTable columns={columns} data={data} isLoading={isLoading} />

      <Modal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        title="Create Beer Order"
        description="Fill in the details to create a new beer order."
        submitLabel="Create Order"
        onSubmit={() => {
          const form = document.getElementById('order-form') as HTMLFormElement
          form?.requestSubmit()
        }}
        isLoading={isSubmitting}
      >
        <OrderForm onSubmit={handleCreateOrder} isLoading={isSubmitting} />
      </Modal>
    </div>
  )
}
