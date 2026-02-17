import React, { useState, useEffect } from 'react'
import { Input } from '@/components/ui/input'
import { FormField } from '@/components/FormField'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import type { BeerOrderShipment } from '@/api/models/BeerOrderShipment'
import type { BeerOrder } from '@/api/models/BeerOrder'
import { listOrders } from '@/features/orders/api/orders'

interface ShipmentFormProps {
  initialData?: BeerOrderShipment
  onSubmit: (data: BeerOrderShipment) => void
  isLoading?: boolean
}

export function ShipmentForm({ initialData, onSubmit, isLoading }: ShipmentFormProps) {
  const [beerOrderId, setBeerOrderId] = useState<string>(initialData?.beerOrderId?.toString() || '')
  const [carrier, setCarrier] = useState(initialData?.carrier || '')
  const [trackingNumber, setTrackingNumber] = useState(initialData?.trackingNumber || '')
  const [shipmentDate, setShipmentDate] = useState(
    initialData?.shipmentDate ? new Date(initialData.shipmentDate).toISOString().split('T')[0] : ''
  )
  
  const [orders, setOrders] = useState<BeerOrder[]>([])

  useEffect(() => {
    listOrders().then(data => setOrders(data))
  }, [])

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSubmit({
      ...initialData,
      beerOrderId: beerOrderId ? parseInt(beerOrderId) : undefined,
      carrier,
      trackingNumber,
      shipmentDate: shipmentDate ? new Date(shipmentDate).toISOString() : undefined
    })
  }

  return (
    <form id="shipment-form" onSubmit={handleSubmit} className="space-y-4">
      <FormField label="Beer Order" required>
        <Select value={beerOrderId} onValueChange={setBeerOrderId} disabled={!!initialData?.id}>
          <SelectTrigger>
            <SelectValue placeholder="Select an order" />
          </SelectTrigger>
          <SelectContent>
            {orders.map(o => (
              <SelectItem key={o.id} value={o.id?.toString() || ''}>
                Order #{o.id} ({o.customerRef})
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </FormField>

      <FormField label="Carrier" required>
        <Input 
          value={carrier} 
          onChange={(e) => setCarrier(e.target.value)} 
          placeholder="e.g. UPS, FedEx"
          required
        />
      </FormField>

      <FormField label="Tracking Number" required>
        <Input 
          value={trackingNumber} 
          onChange={(e) => setTrackingNumber(e.target.value)} 
          placeholder="Enter tracking number"
          required
        />
      </FormField>

      <FormField label="Shipment Date" required>
        <Input 
          type="date"
          value={shipmentDate} 
          onChange={(e) => setShipmentDate(e.target.value)}
          required
        />
      </FormField>
    </form>
  )
}
