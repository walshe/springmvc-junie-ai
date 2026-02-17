import React, { useEffect, useState } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { Beer } from '@/api/models/Beer'
import { getBeerById } from './api/beers'
import { Button } from '@/components/ui/button'
import { Card, CardHeader, CardTitle, CardContent, CardFooter } from '@/components/ui/card'
import { ChevronLeft, Loader2 } from 'lucide-react'

function BeerDetailPage() {
  const { beerId } = useParams<{ beerId: string }>()
  const navigate = useNavigate()
  const [beer, setBeer] = useState<Beer | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    async function loadBeer() {
      if (!beerId) return
      setIsLoading(true)
      try {
        const data = await getBeerById(Number(beerId))
        setBeer(data)
      } catch (err) {
        console.error('Failed to load beer:', err)
        setError('Failed to load beer details. It might not exist or there was a server error.')
      } finally {
        setIsLoading(false)
      }
    }

    loadBeer()
  }, [beerId])

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
        <span className="ml-2">Loading beer details...</span>
      </div>
    )
  }

  if (error || !beer) {
    return (
      <div className="space-y-4">
        <div className="p-4 border border-destructive/50 bg-destructive/10 text-destructive rounded-md">
          {error || 'Beer not found.'}
        </div>
        <Button variant="outline" onClick={() => navigate('/beers')}>
          <ChevronLeft className="h-4 w-4 mr-2" />
          Back to Beers
        </Button>
      </div>
    )
  }

  return (
    <div className="space-y-6 max-w-2xl">
      <div className="flex items-center justify-between">
        <Button variant="ghost" asChild className="-ml-2">
          <Link to="/beers">
            <ChevronLeft className="h-4 w-4 mr-2" />
            Back to Beers
          </Link>
        </Button>
        <div className="flex gap-2">
           {/* Future Story 5.3: Edit/Delete buttons can go here */}
        </div>
      </div>

      <Card>
        <CardHeader>
          <div className="flex justify-between items-start">
            <div>
              <CardTitle className="text-2xl">{beer.beerName}</CardTitle>
              <p className="text-muted-foreground font-medium">{beer.beerStyle}</p>
            </div>
            <div className="text-right">
              <span className="text-2xl font-bold">${beer.price.toFixed(2)}</span>
            </div>
          </div>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <h4 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider">UPC</h4>
              <p className="text-lg font-mono">{beer.upc}</p>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider">Quantity on Hand</h4>
              <p className="text-lg tabular-nums">{beer.quantityOnHand}</p>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider">Created Date</h4>
              <p className="text-sm">
                {beer.createdDate ? new Date(beer.createdDate).toLocaleString() : 'N/A'}
              </p>
            </div>
            <div>
              <h4 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider">Last Updated</h4>
              <p className="text-sm">
                {beer.updateDate ? new Date(beer.updateDate).toLocaleString() : 'N/A'}
              </p>
            </div>
          </div>
        </CardContent>
        <CardFooter className="border-t bg-muted/50 p-4 text-xs text-muted-foreground flex justify-between">
           <span>Beer ID: {beer.id}</span>
           <span>Version: {beer.version}</span>
        </CardFooter>
      </Card>
    </div>
  )
}

export default BeerDetailPage
