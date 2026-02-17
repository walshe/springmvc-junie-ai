import api from '@/lib/api';
import type { Beer } from '@/api/models/Beer';

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; // current page index (1-based as per API contract)
  first: boolean;
  last: boolean;
  empty: boolean;
};

export type ListBeersParams = {
  page: number;
  size: number;
  beerName?: string;
  beerStyle?: string;
};

export async function listBeers(params: ListBeersParams) {
  const { data } = await api.get<Page<Beer>>('/api/v1/beer', { params });
  return data;
}

export async function getBeerById(beerId: number) {
  const { data } = await api.get<Beer>(`/api/v1/beer/${beerId}`);
  return data;
}

export async function createBeer(payload: Beer) {
  const { data } = await api.post<Beer>('/api/v1/beer', payload);
  return data;
}

export async function updateBeer(beerId: number, payload: Beer) {
  await api.put<void>(`/api/v1/beer/${beerId}`, payload);
}

export async function deleteBeer(beerId: number) {
  await api.delete<void>(`/api/v1/beer/${beerId}`);
}
