import api from '@/lib/api';
import type { BeerOrder } from '@/api/models/BeerOrder';
import type { CreateBeerOrderCommand } from '@/api/models/CreateBeerOrderCommand';
import type { PatchBeerOrderCommand } from '@/api/models/PatchBeerOrderCommand';

export async function listOrders() {
  const { data } = await api.get<BeerOrder[]>('/api/v1/beerOrder');
  return data;
}

export async function getOrderById(orderId: number) {
  const { data } = await api.get<BeerOrder>(`/api/v1/beerOrder/${orderId}`);
  return data;
}

export async function createOrder(payload: CreateBeerOrderCommand) {
  const { data } = await api.post<BeerOrder>('/api/v1/beerOrder', payload);
  return data;
}

export async function patchOrder(orderId: number, payload: PatchBeerOrderCommand) {
  await api.patch<void>(`/api/v1/beerOrder/${orderId}`, payload);
}
