import React from 'react'
import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { Plus, Pencil, Trash2 } from 'lucide-react'
import { toast } from 'sonner'
import type { Beer } from '@/api/models/Beer'
import { listBeers, createBeer, updateBeer, deleteBeer } from './api/beers'
import { DataTable } from '@/components/DataTable'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Modal } from '@/components/Modal'
import { BeerForm } from './components/BeerForm'

function BeersPage() {
  const [beerName, setBeerName] = useState<string>('')
  const [beerStyle, setBeerStyle] = useState<string>('')
  const [pageIndex, setPageIndex] = useState<number>(0) // UI 0-based; API expects 1-based
  const [pageSize] = useState<number>(10)

  const [data, setData] = useState<Beer[]>([])
  const [pageCount, setPageCount] = useState<number>(1)
  const [isLoading, setIsLoading] = useState<boolean>(false)

  // Modal states
  const [isCreateOpen, setIsCreateOpen] = useState(false)
  const [isEditOpen, setIsEditOpen] = useState(false)
  const [isDeleteOpen, setIsDeleteOpen] = useState(false)
  const [isSaving, setIsSaving] = useState(false)
  const [selectedBeer, setSelectedBeer] = useState<Beer | null>(null)
  const [formData, setFormData] = useState<Partial<Beer>>({})

  const load = async () => {
    setIsLoading(true)
    try {
      const res = await listBeers({
        page: pageIndex + 1, // API is 1-based
        size: pageSize,
        beerName: beerName || undefined,
        beerStyle: beerStyle || undefined,
      })
      setData(res.content ?? [])
      setPageCount(res.totalPages ?? 1)
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    load()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pageIndex, pageSize])

  const onSubmitFilters = (e: React.FormEvent) => {
    e.preventDefault()
    setPageIndex(0)
    load()
  }

  const handleCreate = async () => {
    setIsSaving(true)
    try {
      await createBeer(formData as Beer)
      toast.success('Beer created successfully')
      setIsCreateOpen(false)
      load()
    } catch (error) {
      toast.error('Failed to create beer')
    } finally {
      setIsSaving(false)
    }
  }

  const handleUpdate = async () => {
    if (!selectedBeer?.id) return
    setIsSaving(true)
    try {
      await updateBeer(selectedBeer.id, formData as Beer)
      toast.success('Beer updated successfully')
      setIsEditOpen(false)
      load()
    } catch (error) {
      toast.error('Failed to update beer')
    } finally {
      setIsSaving(false)
    }
  }

  const handleDelete = async () => {
    if (!selectedBeer?.id) return
    setIsSaving(true)
    try {
      await deleteBeer(selectedBeer.id)
      toast.success('Beer deleted successfully')
      setIsDeleteOpen(false)
      load()
    } catch (error) {
      toast.error('Failed to delete beer')
    } finally {
      setIsSaving(false)
    }
  }

  const columns = useMemo(
    () => [
      {
        header: 'Name',
        accessorKey: (b: Beer) => (
          <Link
            to={`/beers/${b.id}`}
            className="text-primary hover:underline font-medium"
          >
            {b.beerName}
          </Link>
        ),
      },
      { header: 'Style', accessorKey: 'beerStyle' as const },
      { header: 'UPC', accessorKey: 'upc' as const },
      {
        header: 'Qty',
        accessorKey: (b: Beer) => (
          <span className="tabular-nums">{b.quantityOnHand}</span>
        ),
        className: 'text-right',
      },
      {
        header: 'Price',
        accessorKey: (b: Beer) => (
          <span className="tabular-nums">${b.price.toFixed(2)}</span>
        ),
        className: 'text-right',
      },
      {
        header: 'Actions',
        accessorKey: (b: Beer) => (
          <div className="flex justify-end gap-2">
            <Button
              variant="ghost"
              size="icon"
              onClick={() => {
                setSelectedBeer(b)
                setFormData(b)
                setIsEditOpen(true)
              }}
            >
              <Pencil className="h-4 w-4" />
            </Button>
            <Button
              variant="ghost"
              size="icon"
              className="text-destructive hover:text-destructive"
              onClick={() => {
                setSelectedBeer(b)
                setIsDeleteOpen(true)
              }}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        ),
        className: 'text-right',
      },
    ],
    []
  )

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-xl font-semibold">Beers</h2>
          <p className="text-sm text-muted-foreground">
            Filter by name or style. Results are server-side paginated.
          </p>
        </div>
        <Button onClick={() => setIsCreateOpen(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Create Beer
        </Button>
      </div>

      <form onSubmit={onSubmitFilters} className="flex flex-wrap gap-2 items-end">
        <div className="flex flex-col gap-1">
          <label className="text-sm" htmlFor="beerName">Beer name</label>
          <Input
            id="beerName"
            placeholder="e.g. IPA"
            value={beerName}
            onChange={(e) => setBeerName(e.target.value)}
          />
        </div>
        <div className="flex flex-col gap-1">
          <label className="text-sm" htmlFor="beerStyle">Beer style</label>
          <Input
            id="beerStyle"
            placeholder="e.g. LAGER, STOUT"
            value={beerStyle}
            onChange={(e) => setBeerStyle(e.target.value)}
          />
        </div>
        <Button type="submit" disabled={isLoading}>Apply</Button>
      </form>

      <DataTable<Beer>
        columns={columns}
        data={data}
        isLoading={isLoading}
        pagination={{
          pageIndex,
          pageSize,
          pageCount,
          onPageChange: (next) => {
            if (next < 0) return
            if (next > pageCount - 1) return
            setPageIndex(next)
          },
        }}
      />

      {/* Create Modal */}
      <Modal
        isOpen={isCreateOpen}
        onClose={() => setIsCreateOpen(false)}
        title="Create Beer"
        description="Fill in the details to add a new beer to the inventory."
        onSubmit={handleCreate}
        isLoading={isSaving}
        submitLabel="Create"
      >
        <BeerForm onChange={setFormData} onSubmit={handleCreate} />
      </Modal>

      {/* Edit Modal */}
      <Modal
        isOpen={isEditOpen}
        onClose={() => setIsEditOpen(false)}
        title="Edit Beer"
        description="Update the details of the selected beer."
        onSubmit={handleUpdate}
        isLoading={isSaving}
        submitLabel="Update"
      >
        <BeerForm
          initialData={selectedBeer || {}}
          onChange={setFormData}
          onSubmit={handleUpdate}
        />
      </Modal>

      {/* Delete Confirmation Modal */}
      <Modal
        isOpen={isDeleteOpen}
        onClose={() => setIsDeleteOpen(false)}
        title="Delete Beer"
        description={`Are you sure you want to delete "${selectedBeer?.beerName}"? This action cannot be undone.`}
        onSubmit={handleDelete}
        isLoading={isSaving}
        submitLabel="Delete"
      />
    </div>
  )
}

export default BeersPage
