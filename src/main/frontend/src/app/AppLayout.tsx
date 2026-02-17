import React from 'react'
import Nav from './Nav'

type Props = {
  children?: React.ReactNode
}

function AppLayout({ children }: Props) {
  return (
    <div className="min-h-screen flex flex-col">
      <header className="border-b">
        <div className="container mx-auto px-4 py-3">
          <div className="flex items-center justify-between gap-4">
            <h1 className="text-xl font-semibold">JunieMVC</h1>
            <Nav />
          </div>
        </div>
      </header>
      <main className="flex-1">
        <div className="container mx-auto px-4 py-6">{children}</div>
      </main>
    </div>
  )
}

export default AppLayout
