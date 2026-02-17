import React, { useState, useEffect } from 'react'
import { Plus, Trash2 } from 'lucide-react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { FormField } from '@/components/FormField'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import type { CreateBeerOrderCommand } from '@/api/models/CreateBeerOrderCommand'
import type { CreateBeerOrderLineCommand } from '@/api/models/CreateBeerOrderLineCommand'
import type { Beer } from '@/api/models/Beer'
import type { Customer } from '@/api/models/Customer'
import { listBeers } from '@/features/beers/api/beers'
import { listCustomers } from '@/features/customers/api/customers'

interface OrderFormProps {
  onSubmit: (data: CreateBeerOrderCommand) => void
  isLoading?: boolean
}

export function OrderForm({ onSubmit, isLoading }: OrderFormProps) {
  const [customerRef, setCustomerRef] = useState('')
  const [notes, setNotes] = useState('')
  const [lines, setLines] = useState<CreateBeerOrderLineCommand[]>([{ beerId: 0, orderQuantity: 1 }])
  
  const [beers, setBeers] = useState<Beer[]>([])
  const [customers, setCustomers] = useState<Customer[]>([])

  useEffect(() => {
    listBeers({ page: 1, size: 100 }).then(data => setBeers(data.content || []))
    listCustomers().then(data => setCustomers(data))
  }, [])

  const addLine = () => {
    setLines([...lines, { beerId: 0, orderQuantity: 1 }])
  }

  const removeLine = (index: number) => {
    setLines(lines.filter((_, i) => i !== index))
  }

  const updateLine = (index: number, field: keyof CreateBeerOrderLineCommand, value: number) => {
    const newLines = [...lines]
    newLines[index] = { ...newLines[index], [field]: value }
    setLines(newLines)
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    onSubmit({
      customerRef,
      notes,
      beerOrderLines: lines.filter(l => l.beerId > 0 && l.orderQuantity > 0)
    })
  }

  return (
    <form id="order-form" onSubmit={handleSubmit} className="space-y-6">
      <div className="space-y-4">
        <FormField label="Customer" required>
          <Select value={customerRef} onValueChange={setCustomerRef}>
            <SelectTrigger>
              <SelectValue placeholder="Select a customer" />
            </SelectTrigger>
            <SelectContent>
              {customers.map(c => (
                <SelectItem key={c.id} value={c.name}>
                  {c.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </FormField>

        <FormField label="Notes">
          <Textarea 
            value={notes} 
            onChange={(e) => setNotes(e.target.value)} 
            placeholder="Optional order notes"
          />
        </FormField>
      </div>

      <div className="space-y-4">
        <div className="flex items-center justify-between">
          <Label className="text-base font-semibold">Order Items</Label>
          <Button type="button" variant="outline" size="sm" onClick={addLine}>
            <Plus className="mr-2 h-4 w-4" />
            Add Item
          </Button>
        </div>

        {lines.map((line, index) => (
          <div key={index} className="flex items-end gap-4 rounded-lg border p-4 bg-gray-50/50">
            <div className="flex-1">
              <FormField label="Beer" required>
                <Select 
                  value={line.beerId.toString()} 
                  onValueChange={(val) => updateLine(index, 'beerId', parseInt(val))}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select beer" />
                  </SelectTrigger>
                  <SelectContent>
                    {beers.map(b => (
                      <SelectItem key={b.id} value={b.id?.toString() || ''}>
                        {b.beerName} ({b.beerStyle})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </FormField>
            </div>
            <div className="w-32">
              <FormField label="Quantity" required>
                <Input 
                  type="number" 
                  min="1" 
                  value={line.orderQuantity} 
                  onChange={(e) => updateLine(index, 'orderQuantity', parseInt(e.target.value) || 0)}
                />
              </FormField>
            </div>
            <Button 
              type="button" 
              variant="ghost" 
              size="icon" 
              className="text-destructive"
              onClick={() => removeLine(index)}
              disabled={lines.length === 1}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        ))}
      </div>
    </form>
  )
}
