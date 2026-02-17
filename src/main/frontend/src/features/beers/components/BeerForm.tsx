import React from 'react'
import { useState, useEffect } from 'react'
import type { Beer } from '@/api/models/Beer'
import { FormField } from '@/components/FormField'
import { Input } from '@/components/ui/input'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

interface BeerFormProps {
  initialData?: Partial<Beer>
  onChange: (data: Partial<Beer>) => void
  errors?: Record<string, string>
  onSubmit?: (e: React.FormEvent) => void
}

const BEER_STYLES = ['LAGER', 'PILSNER', 'STOUT', 'GOSE', 'PORTER', 'ALE', 'WHEAT', 'IPA', 'SAISON']

export function BeerForm({ initialData, onChange, errors, onSubmit }: BeerFormProps) {
  const [formData, setFormData] = useState<Partial<Beer>>({
    beerName: '',
    beerStyle: '',
    upc: '',
    price: 0,
    quantityOnHand: 0,
    ...initialData,
  })

  useEffect(() => {
    onChange(formData)
  }, [formData, onChange])

  const handleChange = (field: keyof Beer, value: string | number) => {
    setFormData((prev) => ({ ...prev, [field]: value }))
  }

  return (
    <form className="grid gap-4" onSubmit={onSubmit}>
      <FormField label="Beer Name" required error={errors?.beerName}>
        <Input
          value={formData.beerName}
          onChange={(e) => handleChange('beerName', e.target.value)}
          placeholder="Enter beer name"
        />
      </FormField>

      <FormField label="Beer Style" required error={errors?.beerStyle}>
        <Select
          value={formData.beerStyle}
          onValueChange={(value) => handleChange('beerStyle', value)}
        >
          <SelectTrigger>
            <SelectValue placeholder="Select style" />
          </SelectTrigger>
          <SelectContent>
            {BEER_STYLES.map((style) => (
              <SelectItem key={style} value={style}>
                {style}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </FormField>

      <FormField label="UPC" required error={errors?.upc}>
        <Input
          value={formData.upc}
          onChange={(e) => handleChange('upc', e.target.value)}
          placeholder="UPC code (13 digits)"
        />
      </FormField>

      <div className="grid grid-cols-2 gap-4">
        <FormField label="Price" required error={errors?.price}>
          <Input
            type="number"
            step="0.01"
            value={formData.price}
            onChange={(e) => handleChange('price', parseFloat(e.target.value) || 0)}
          />
        </FormField>

        <FormField label="Quantity" required error={errors?.quantityOnHand}>
          <Input
            type="number"
            value={formData.quantityOnHand}
            onChange={(e) => handleChange('quantityOnHand', parseInt(e.target.value, 10) || 0)}
          />
        </FormField>
      </div>
    </form>
  )
}
