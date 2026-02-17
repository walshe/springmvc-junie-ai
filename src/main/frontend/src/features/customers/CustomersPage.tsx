import React from 'react'
import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { Plus, Pencil, Trash2 } from 'lucide-react'
import { toast } from 'sonner'
import type { Customer } from '@/api/models/Customer'
import type { CreateCustomerCommand } from '@/api/models/CreateCustomerCommand'
import type { UpdateCustomerCommand } from '@/api/models/UpdateCustomerCommand'
import { listCustomers, createCustomer, updateCustomer, deleteCustomer } from './api/customers'
import { DataTable } from '@/components/DataTable'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Modal } from '@/components/Modal'
import { CustomerForm, type CustomerFormValues } from './components/CustomerForm'

function CustomersPage() {
  const [query, setQuery] = useState<string>('')
  const [data, setData] = useState<Customer[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [sortAsc, setSortAsc] = useState<boolean>(true)

  const [isCreateOpen, setIsCreateOpen] = useState<boolean>(false)
  const [isEditOpen, setIsEditOpen] = useState<boolean>(false)
  const [isDeleteOpen, setIsDeleteOpen] = useState<boolean>(false)
  const [selected, setSelected] = useState<Customer | null>(null)
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false)
  const [formData, setFormData] = useState<CustomerFormValues>({
    name: '',
    email: '',
    phone_number: '',
    address_line_1: '',
    address_line_2: '',
    city: '',
    state: '',
    postal_code: '',
  })

  async function load() {
    try {
      setIsLoading(true)
      const customers = await listCustomers()
      setData(customers)
    } catch (e) {
      toast.error('Failed to load customers')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [])

  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase()
    const base = q
      ? data.filter((c) =>
          [c.name, c.email ?? '', c.phone_number ?? '', c.city, c.state]
            .join(' ')
            .toLowerCase()
            .includes(q)
        )
      : data
    const sorted = [...base].sort((a, b) => {
      const an = a.name.toLowerCase()
      const bn = b.name.toLowerCase()
      if (an < bn) return sortAsc ? -1 : 1
      if (an > bn) return sortAsc ? 1 : -1
      return 0
    })
    return sorted
  }, [data, query, sortAsc])

  function openCreate() {
    setSelected(null)
    setIsCreateOpen(true)
  }

  function openEdit(c: Customer) {
    setSelected(c)
    setIsEditOpen(true)
  }

  function openDelete(c: Customer) {
    setSelected(c)
    setIsDeleteOpen(true)
  }

  async function handleCreate() {
    try {
      setIsSubmitting(true)
      await createCustomer(formData as CreateCustomerCommand)
      toast.success('Customer created')
      setIsCreateOpen(false)
      await load()
    } catch (e: any) {
      toast.error('Failed to create customer')
    } finally {
      setIsSubmitting(false)
    }
  }

  async function handleUpdate() {
    if (!selected?.id) return
    try {
      setIsSubmitting(true)
      await updateCustomer(selected.id, formData as UpdateCustomerCommand)
      toast.success('Customer updated')
      setIsEditOpen(false)
      await load()
    } catch (e: any) {
      toast.error('Failed to update customer')
    } finally {
      setIsSubmitting(false)
    }
  }

  async function handleDelete() {
    if (!selected?.id) return
    try {
      setIsSubmitting(true)
      await deleteCustomer(selected.id)
      toast.success('Customer deleted')
      setIsDeleteOpen(false)
      await load()
    } catch (e: any) {
      toast.error('Failed to delete customer')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="space-y-4">
      <div className="flex items-end justify-between gap-2">
        <div className="space-y-2">
          <h2 className="text-xl font-semibold">Customers</h2>
          <div className="flex items-center gap-2">
            <Input
              placeholder="Search by name, email, phone, city, state"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
            />
            <Button variant="outline" onClick={() => setSortAsc((s) => !s)}>
              Sort: {sortAsc ? 'A→Z' : 'Z→A'}
            </Button>
          </div>
        </div>
        <Button onClick={openCreate}>
          <Plus className="mr-2 h-4 w-4" /> Create Customer
        </Button>
      </div>

      <DataTable
        columns={[
          {
            header: 'Name',
            accessorKey: (c: Customer) => (
              <Link to={`/customers/${c.id}`} className="text-primary hover:underline">
                {c.name}
              </Link>
            ),
          },
          { header: 'Email', accessorKey: 'email' },
          { header: 'Phone', accessorKey: 'phone_number' },
          { header: 'City', accessorKey: 'city' },
          { header: 'State', accessorKey: 'state' },
          {
            header: 'Actions',
            accessorKey: (c: Customer) => (
              <div className="flex items-center gap-2 justify-end">
                <Button variant="outline" size="sm" onClick={() => openEdit(c)}>
                  <Pencil className="h-4 w-4" />
                </Button>
                <Button variant="destructive" size="sm" onClick={() => openDelete(c)}>
                  <Trash2 className="h-4 w-4" />
                </Button>
              </div>
            ),
            className: 'text-right',
          },
        ]}
        data={filtered}
        isLoading={isLoading}
      />

      {/* Create Modal */}
      <Modal
        isOpen={isCreateOpen}
        onClose={() => setIsCreateOpen(false)}
        title="Create Customer"
        isLoading={isSubmitting}
        submitLabel="Create"
        onSubmit={handleCreate}
      >
        <CustomerForm
          onSubmit={handleCreate}
          onChange={setFormData}
        />
      </Modal>

      {/* Edit Modal */}
      <Modal
        isOpen={isEditOpen}
        onClose={() => setIsEditOpen(false)}
        title="Edit Customer"
        isLoading={isSubmitting}
        submitLabel="Save"
        onSubmit={handleUpdate}
      >
        {selected && (
          <CustomerForm
            initialValues={{
              name: selected.name,
              email: selected.email ?? '',
              phone_number: selected.phone_number ?? '',
              address_line_1: selected.address_line_1,
              address_line_2: selected.address_line_2 ?? '',
              city: selected.city,
              state: selected.state,
              postal_code: selected.postal_code,
            }}
            onSubmit={handleUpdate}
            onChange={setFormData}
          />
        )}
      </Modal>

      {/* Delete Modal */}
      <Modal
        isOpen={isDeleteOpen}
        onClose={() => setIsDeleteOpen(false)}
        title="Delete Customer"
        description={`Are you sure you want to delete ${selected?.name}? This action cannot be undone.`}
        isLoading={isSubmitting}
        submitLabel="Delete"
        onSubmit={handleDelete}
      />
    </div>
  )
}

export default CustomersPage
