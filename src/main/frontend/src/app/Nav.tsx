import React from 'react'
import { NavLink } from 'react-router-dom'

const linkBase = 'px-3 py-2 rounded-md text-sm font-medium'
const linkActive = 'bg-black text-white'
const linkInactive = 'text-gray-700 hover:bg-gray-100'

function Nav() {
  const classes = ({ isActive }: { isActive: boolean }) =>
    [linkBase, isActive ? linkActive : linkInactive].join(' ')

  return (
    <nav className="flex items-center gap-2">
      <NavLink to="/beers" className={classes}>
        Beers
      </NavLink>
      <NavLink to="/customers" className={classes}>
        Customers
      </NavLink>
      <NavLink to="/orders" className={classes}>
        Orders
      </NavLink>
      <NavLink to="/shipments" className={classes}>
        Shipments
      </NavLink>
    </nav>
  )
}

export default Nav
