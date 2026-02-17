import api from '@/lib/api';
import type { Customer } from '@/api/models/Customer';
import type { CreateCustomerCommand } from '@/api/models/CreateCustomerCommand';
import type { UpdateCustomerCommand } from '@/api/models/UpdateCustomerCommand';

export async function listCustomers() {
  const { data } = await api.get<Customer[]>('/api/v1/customers');
  return data;
}

export async function getCustomerById(customerId: number) {
  const { data } = await api.get<Customer>(`/api/v1/customers/${customerId}`);
  return data;
}

export async function createCustomer(payload: CreateCustomerCommand) {
  const { data } = await api.post<Customer>('/api/v1/customers', payload);
  return data;
}

export async function updateCustomer(customerId: number, payload: UpdateCustomerCommand) {
  await api.put<void>(`/api/v1/customers/${customerId}`, payload);
}

export async function deleteCustomer(customerId: number) {
  await api.delete<void>(`/api/v1/customers/${customerId}`);
}
