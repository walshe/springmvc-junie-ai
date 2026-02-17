import React from 'react'
import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import type { Customer } from '@/api/models/Customer'
import { getCustomerById } from './api/customers'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'

function CustomerDetailPage() {
  const { customerId } = useParams()
  const [customer, setCustomer] = useState<Customer | null>(null)
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    async function fetchCustomer() {
      if (!customerId) return
      try {
        setIsLoading(true)
        const data = await getCustomerById(Number(customerId))
        setCustomer(data)
      } catch (e) {
        setError('Failed to load customer')
      } finally {
        setIsLoading(false)
      }
    }
    fetchCustomer()
  }, [customerId])

  return (
    <div className="space-y-4">
      <div>
        <Link to="/customers">
          <Button variant="outline">Back to Customers</Button>
        </Link>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>Customer Details</CardTitle>
        </CardHeader>
        <CardContent>
          {isLoading ? (
            <p>Loading...</p>
          ) : error ? (
            <p className="text-destructive">{error}</p>
          ) : customer ? (
            <div className="grid grid-cols-1 gap-2 sm:grid-cols-2">
              <div>
                <div className="text-sm text-muted-foreground">Name</div>
                <div className="font-medium">{customer.name}</div>
              </div>
              {customer.email && (
                <div>
                  <div className="text-sm text-muted-foreground">Email</div>
                  <div className="font-medium">{customer.email}</div>
                </div>
              )}
              {customer.phone_number && (
                <div>
                  <div className="text-sm text-muted-foreground">Phone</div>
                  <div className="font-medium">{customer.phone_number}</div>
                </div>
              )}
              <div className="sm:col-span-2">
                <div className="text-sm text-muted-foreground">Address</div>
                <div className="font-medium">
                  {customer.address_line_1}
                  {customer.address_line_2 ? `, ${customer.address_line_2}` : ''}
                  , {customer.city}, {customer.state} {customer.postal_code}
                </div>
              </div>
              {customer.created_date && (
                <div>
                  <div className="text-sm text-muted-foreground">Created</div>
                  <div className="font-medium">{new Date(customer.created_date).toLocaleString()}</div>
                </div>
              )}
              {customer.update_date && (
                <div>
                  <div className="text-sm text-muted-foreground">Updated</div>
                  <div className="font-medium">{new Date(customer.update_date).toLocaleString()}</div>
                </div>
              )}
            </div>
          ) : (
            <p>No customer found.</p>
          )}
        </CardContent>
      </Card>
    </div>
  )
}

export default CustomerDetailPage
