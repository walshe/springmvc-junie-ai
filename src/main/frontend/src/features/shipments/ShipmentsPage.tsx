import React, { useEffect, useState } from 'react'
import { Plus, Pencil, Trash2 } from 'lucide-react'
import { toast } from 'sonner'
import type { BeerOrderShipment } from '@/api/models/BeerOrderShipment'
import { listShipments, createShipment, updateShipment, deleteShipment } from './api/shipments'
import { DataTable } from '@/components/DataTable'
import { Button } from '@/components/ui/button'
import { Modal } from '@/components/Modal'
import { ShipmentForm } from './components/ShipmentForm'

export default function ShipmentsPage() {
  const [data, setData] = useState<BeerOrderShipment[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)
  
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false)
  const [isEditModalOpen, setIsEditModalOpen] = useState(false)
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [selectedShipment, setSelectedShipment] = useState<BeerOrderShipment | null>(null)

  const fetchShipments = async () => {
    setIsLoading(true)
    try {
      const shipments = await listShipments()
      setData(shipments)
    } catch (error) {
      console.error('Failed to fetch shipments:', error)
      toast.error('Failed to load shipments')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchShipments()
  }, [])

  const handleCreateShipment = async (command: BeerOrderShipment) => {
    setIsSubmitting(true)
    try {
      await createShipment(command)
      toast.success('Shipment created successfully')
      setIsCreateModalOpen(false)
      fetchShipments()
    } catch (error) {
      console.error('Failed to create shipment:', error)
      toast.error('Failed to create shipment')
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleUpdateShipment = async (command: BeerOrderShipment) => {
    if (!selectedShipment?.id) return
    setIsSubmitting(true)
    try {
      await updateShipment(selectedShipment.id, command)
      toast.success('Shipment updated successfully')
      setIsEditModalOpen(false)
      fetchShipments()
    } catch (error) {
      console.error('Failed to update shipment:', error)
      toast.error('Failed to update shipment')
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleDeleteShipment = async () => {
    if (!selectedShipment?.id) return
    setIsSubmitting(true)
    try {
      await deleteShipment(selectedShipment.id)
      toast.success('Shipment deleted successfully')
      setIsDeleteModalOpen(false)
      fetchShipments()
    } catch (error) {
      console.error('Failed to delete shipment:', error)
      toast.error('Failed to delete shipment')
    } finally {
      setIsSubmitting(false)
    }
  }

  const columns = [
    {
      header: 'ID',
      accessorKey: 'id' as keyof BeerOrderShipment,
      className: 'w-[80px]',
    },
    {
      header: 'Order ID',
      accessorKey: 'beerOrderId' as keyof BeerOrderShipment,
    },
    {
      header: 'Carrier',
      accessorKey: 'carrier' as keyof BeerOrderShipment,
    },
    {
      header: 'Tracking Number',
      accessorKey: 'trackingNumber' as keyof BeerOrderShipment,
    },
    {
      header: 'Ship Date',
      accessorKey: (shipment: BeerOrderShipment) =>
        shipment.shipmentDate ? new Date(shipment.shipmentDate).toLocaleDateString() : '-',
    },
    {
      header: 'Actions',
      accessorKey: (shipment: BeerOrderShipment) => (
        <div className="flex items-center gap-2">
          <Button
            variant="ghost"
            size="icon"
            onClick={() => {
              setSelectedShipment(shipment)
              setIsEditModalOpen(true)
            }}
          >
            <Pencil className="h-4 w-4" />
          </Button>
          <Button
            variant="ghost"
            size="icon"
            className="text-destructive"
            onClick={() => {
              setSelectedShipment(shipment)
              setIsDeleteModalOpen(true)
            }}
          >
            <Trash2 className="h-4 w-4" />
          </Button>
        </div>
      ),
      className: 'text-right',
    },
  ]

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Shipments</h1>
          <p className="text-muted-foreground">
            Manage beer order shipments and tracking.
          </p>
        </div>
        <Button onClick={() => setIsCreateModalOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Create Shipment
        </Button>
      </div>

      <DataTable columns={columns} data={data} isLoading={isLoading} />

      <Modal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        title="Create Shipment"
        description="Fill in the details to create a new shipment."
        submitLabel="Create Shipment"
        onSubmit={() => {
          const form = document.getElementById('shipment-form') as HTMLFormElement
          form?.requestSubmit()
        }}
        isLoading={isSubmitting}
      >
        <ShipmentForm onSubmit={handleCreateShipment} isLoading={isSubmitting} />
      </Modal>

      <Modal
        isOpen={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        title="Edit Shipment"
        description="Update shipment details."
        submitLabel="Update Shipment"
        onSubmit={() => {
          const form = document.getElementById('shipment-form') as HTMLFormElement
          form?.requestSubmit()
        }}
        isLoading={isSubmitting}
      >
        {selectedShipment && (
          <ShipmentForm 
            initialData={selectedShipment} 
            onSubmit={handleUpdateShipment} 
            isLoading={isSubmitting} 
          />
        )}
      </Modal>

      <Modal
        isOpen={isDeleteModalOpen}
        onClose={() => setIsDeleteModalOpen(false)}
        title="Delete Shipment"
        description={`Are you sure you want to delete shipment #${selectedShipment?.id}? This action cannot be undone.`}
        submitLabel="Delete"
        onSubmit={handleDeleteShipment}
        isLoading={isSubmitting}
      />
    </div>
  )
}
