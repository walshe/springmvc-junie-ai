import React from 'react'
import { useEffect, useState } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { ChevronLeft, Save } from 'lucide-react'
import { toast } from 'sonner'
import type { BeerOrder } from '@/api/models/BeerOrder'
import type { PatchBeerOrderCommand } from '@/api/models/PatchBeerOrderCommand'
import { getOrderById, patchOrder } from './api/orders'
import { getBeerById } from '../beers/api/beers'
import type { Beer } from '@/api/models/Beer'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { DataTable } from '@/components/DataTable'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { FormField } from '@/components/FormField'

export default function OrderDetailPage() {
  const { orderId } = useParams<{ orderId: string }>()
  const navigate = useNavigate()
  const [order, setOrder] = useState<BeerOrder | null>(null)
  const [beers, setBeers] = useState<Record<number, Beer>>({})
  const [isLoading, setIsLoading] = useState<boolean>(true)
  const [isSaving, setIsSaving] = useState<boolean>(false)

  const fetchOrderDetails = async () => {
    if (!orderId) return
    setIsLoading(true)
    try {
      const data = await getOrderById(parseInt(orderId))
      setOrder(data)

      // Fetch beer names for order lines
      if (data.beerOrderLines) {
        const beerIds = [...new Set(data.beerOrderLines.map((l) => l.beerId).filter((id): id is number => id !== undefined))]
        const beerData: Record<number, Beer> = {}
        await Promise.all(
          beerIds.map(async (id) => {
            try {
              const beer = await getBeerById(id)
              beerData[id] = beer
            } catch (err) {
              console.error(`Failed to fetch beer ${id}`, err)
            }
          })
        )
        setBeers(beerData)
      }
    } catch (error) {
      console.error('Failed to fetch order:', error)
      toast.error('Failed to load order details')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchOrderDetails()
  }, [orderId])

  const handleStatusChange = async (newStatus: string) => {
    if (!orderId || !order) return
    setIsSaving(true)
    try {
      await patchOrder(parseInt(orderId), { status: newStatus as PatchBeerOrderCommand.status })
      setOrder({ ...order, status: newStatus as BeerOrder.status })
      toast.success(`Order status updated to ${newStatus}`)
    } catch (error) {
      console.error('Failed to update status:', error)
      toast.error('Failed to update order status')
    } finally {
      setIsSaving(false)
    }
  }

  if (isLoading) {
    return <div className="p-8 text-center">Loading order details...</div>
  }

  if (!order) {
    return (
      <div className="p-8 text-center">
        <h2 className="text-2xl font-bold">Order not found</h2>
        <Button variant="link" onClick={() => navigate('/orders')}>
          Back to Orders
        </Button>
      </div>
    )
  }

  const lineColumns = [
    {
      header: 'Beer',
      accessorKey: (line: any) => {
        const beer = line.beerId ? beers[line.beerId] : null
        return beer ? (
          <Link to={`/beers/${beer.id}`} className="font-medium text-primary hover:underline">
            {beer.beerName}
          </Link>
        ) : (
          `Beer ID: ${line.beerId}`
        )
      },
    },
    {
      header: 'Ordered',
      accessorKey: 'orderQuantity',
      className: 'text-right',
    },
    {
      header: 'Allocated',
      accessorKey: 'quantityAllocated',
      className: 'text-right',
    },
    {
      header: 'Status',
      accessorKey: (line: any) => (
        <Badge variant={line.status === 'DELIVERED' ? 'default' : 'secondary'}>
          {line.status}
        </Badge>
      ),
    },
  ]

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <Button variant="ghost" onClick={() => navigate('/orders')}>
          <ChevronLeft className="mr-2 h-4 w-4" />
          Back to Orders
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        <Card className="md:col-span-2">
          <CardHeader>
            <div className="flex items-center justify-between">
              <div>
                <CardTitle>Order #{order.id}</CardTitle>
                <CardDescription>{order.customerRef || 'No customer reference'}</CardDescription>
              </div>
              <Badge variant={order.status === 'DELIVERED' ? 'default' : 'secondary'} className="text-sm">
                {order.status}
              </Badge>
            </div>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <p className="text-muted-foreground">Created Date</p>
                  <p className="font-medium">
                    {order.createdDate ? new Date(order.createdDate).toLocaleString() : '-'}
                  </p>
                </div>
                <div>
                  <p className="text-muted-foreground">Last Updated</p>
                  <p className="font-medium">
                    {order.updateDate ? new Date(order.updateDate).toLocaleString() : '-'}
                  </p>
                </div>
                <div>
                  <p className="text-muted-foreground">Payment Amount</p>
                  <p className="font-medium text-lg">
                    {order.paymentAmount != null ? `$${order.paymentAmount.toFixed(2)}` : '-'}
                  </p>
                </div>
              </div>

              {order.notes && (
                <div>
                  <p className="text-muted-foreground text-sm">Notes</p>
                  <p className="text-sm">{order.notes}</p>
                </div>
              )}

              <div className="pt-4 border-t">
                <h4 className="mb-4 font-semibold">Order Items</h4>
                <DataTable columns={lineColumns as any} data={order.beerOrderLines || []} />
              </div>
            </div>
          </CardContent>
        </Card>

        <div className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>Actions</CardTitle>
            </CardHeader>
            <CardContent>
              <FormField label="Order Status">
                <Select
                  disabled={isSaving}
                  value={order.status}
                  onValueChange={(value) => handleStatusChange(value as any)}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select status" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="PENDING">PENDING</SelectItem>
                    <SelectItem value="DELIVERED">DELIVERED</SelectItem>
                  </SelectContent>
                </Select>
              </FormField>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
