import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { DataTable } from '../DataTable';

describe('DataTable component', () => {
  const columns = [
    { header: 'ID', accessorKey: 'id' as const },
    { header: 'Name', accessorKey: 'name' as const },
    { 
      header: 'Actions', 
      accessorKey: (item: { id: number, name: string }) => <button>Edit {item.id}</button> 
    }
  ];

  const data = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' },
  ];

  it('renders table headers correctly', () => {
    render(<DataTable columns={columns} data={data} />);
    expect(screen.getByText('ID')).toBeInTheDocument();
    expect(screen.getByText('Name')).toBeInTheDocument();
    expect(screen.getByText('Actions')).toBeInTheDocument();
  });

  it('renders data rows correctly', () => {
    render(<DataTable columns={columns} data={data} />);
    expect(screen.getByText('Item 1')).toBeInTheDocument();
    expect(screen.getByText('Item 2')).toBeInTheDocument();
    expect(screen.getByText('Edit 1')).toBeInTheDocument();
    expect(screen.getByText('Edit 2')).toBeInTheDocument();
  });

  it('shows loading state', () => {
    render(<DataTable columns={columns} data={[]} isLoading={true} />);
    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('shows empty state when no data', () => {
    render(<DataTable columns={columns} data={[]} />);
    expect(screen.getByText('No results.')).toBeInTheDocument();
  });

  it('handles pagination next/prev clicks', () => {
    const onPageChange = vi.fn();
    const pagination = {
      pageIndex: 0,
      pageSize: 10,
      pageCount: 2,
      onPageChange
    };

    const { rerender } = render(<DataTable columns={columns} data={data} pagination={pagination} />);
    
    const nextButton = screen.getByText('Next');
    fireEvent.click(nextButton);
    expect(onPageChange).toHaveBeenCalledWith(1);

    // Re-render with new page index
    rerender(<DataTable columns={columns} data={data} pagination={{ ...pagination, pageIndex: 1 }} />);
    const prevButton = screen.getByText('Previous');
    fireEvent.click(prevButton);
    expect(onPageChange).toHaveBeenCalledWith(0);
  });

  it('disables pagination buttons appropriately', () => {
    const pagination = {
      pageIndex: 0,
      pageSize: 10,
      pageCount: 1,
      onPageChange: vi.fn()
    };

    render(<DataTable columns={columns} data={data} pagination={pagination} />);
    
    expect(screen.getByText('Previous')).toBeDisabled();
    expect(screen.getByText('Next')).toBeDisabled();
  });
});
