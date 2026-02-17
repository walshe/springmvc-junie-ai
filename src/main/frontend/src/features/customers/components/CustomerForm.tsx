import React from 'react'
import { useEffect, useState } from 'react'
import { Input } from '@/components/ui/input'
import { FormField } from '@/components/FormField'
import type { CreateCustomerCommand } from '@/api/models/CreateCustomerCommand'
import type { UpdateCustomerCommand } from '@/api/models/UpdateCustomerCommand'

export type CustomerFormValues = CreateCustomerCommand | UpdateCustomerCommand

interface CustomerFormProps {
  initialValues?: Partial<CustomerFormValues>
  onSubmit: (e?: React.FormEvent) => void
  onChange: (values: CustomerFormValues) => void
}

export function CustomerForm({ initialValues, onSubmit, onChange }: CustomerFormProps) {
  const [values, setValues] = useState<CustomerFormValues>({
    name: '',
    email: '',
    phone_number: '',
    address_line_1: '',
    address_line_2: '',
    city: '',
    state: '',
    postal_code: '',
    ...initialValues,
  })


  useEffect(() => {
    onChange(values)
  }, [values, onChange])

  function update<K extends keyof CustomerFormValues>(key: K, value: CustomerFormValues[K]) {
    setValues((v) => ({ ...v, [key]: value }))
  }

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault()
        onSubmit()
      }}
      className="grid grid-cols-1 gap-3 sm:grid-cols-2"
    >
      <FormField label="Name" required className="sm:col-span-2">
        <Input
          value={values.name}
          onChange={(e) => update('name', e.target.value)}
          required
        />
      </FormField>
      <FormField label="Email">
        <Input
          type="email"
          value={values.email ?? ''}
          onChange={(e) => update('email', e.target.value)}
        />
      </FormField>
      <FormField label="Phone">
        <Input
          value={values.phone_number ?? ''}
          onChange={(e) => update('phone_number', e.target.value)}
        />
      </FormField>
      <FormField label="Address Line 1" required className="sm:col-span-2">
        <Input
          value={values.address_line_1}
          onChange={(e) => update('address_line_1', e.target.value)}
          required
        />
      </FormField>
      <FormField label="Address Line 2" className="sm:col-span-2">
        <Input
          value={values.address_line_2 ?? ''}
          onChange={(e) => update('address_line_2', e.target.value)}
        />
      </FormField>
      <FormField label="City" required>
        <Input
          value={values.city}
          onChange={(e) => update('city', e.target.value)}
          required
        />
      </FormField>
      <FormField label="State" required>
        <Input
          value={values.state}
          onChange={(e) => update('state', e.target.value)}
          required
        />
      </FormField>
      <FormField label="Postal Code" required>
        <Input
          value={values.postal_code}
          onChange={(e) => update('postal_code', e.target.value)}
          required
        />
      </FormField>
    </form>
  )
}
