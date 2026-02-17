import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { toast } from 'sonner'
import type { BeerOrderShipment } from '@/api/models/BeerOrderShipment'
import { getShipmentById } from './api/shipments'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'

export default function ShipmentDetailPage() {
  const { shipmentId } = useParams()
  const [shipment, setShipment] = useState<BeerOrderShipment | null>(null)
  const [isLoading, setIsLoading] = useState(false)

  const fetchShipment = async (id: number) => {
    setIsLoading(true)
    try {
      const data = await getShipmentById(id)
      setShipment(data)
    } catch (error) {
      console.error('Failed to fetch shipment:', error)
      toast.error('Failed to load shipment')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    const id = Number(shipmentId)
    if (!Number.isNaN(id)) {
      fetchShipment(id)
    }
  }, [shipmentId])

  if (isLoading) {
    return <div>Loading...</div>
  }

  if (!shipment) {
    return <div>Shipment not found.</div>
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Shipment #{shipment.id}</h1>
          <p className="text-muted-foreground">Details for this shipment.</p>
        </div>
        <Button asChild variant="outline">
          <Link to="/shipments">Back to Shipments</Link>
        </Button>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Shipment Information</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4 sm:grid-cols-2">
            <div>
              <div className="text-sm text-muted-foreground">Order ID</div>
              <div className="font-medium">{shipment.beerOrderId}</div>
            </div>
            <div>
              <div className="text-sm text-muted-foreground">Carrier</div>
              <div className="font-medium">{shipment.carrier || '-'}</div>
            </div>
            <div>
              <div className="text-sm text-muted-foreground">Tracking Number</div>
              <div className="font-medium">{shipment.trackingNumber || '-'}</div>
            </div>
            <div>
              <div className="text-sm text-muted-foreground">Shipment Date</div>
              <div className="font-medium">
                {shipment.shipmentDate ? new Date(shipment.shipmentDate).toLocaleString() : '-'}
              </div>
            </div>
            <div>
              <div className="text-sm text-muted-foreground">Created</div>
              <div className="font-medium">
                {shipment.createdDate ? new Date(shipment.createdDate).toLocaleString() : '-'}
              </div>
            </div>
            <div>
              <div className="text-sm text-muted-foreground">Updated</div>
              <div className="font-medium">
                {shipment.updateDate ? new Date(shipment.updateDate).toLocaleString() : '-'}
              </div>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
