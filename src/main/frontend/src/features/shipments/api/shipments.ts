import api from '@/lib/api';
import type { BeerOrderShipment } from '@/api/models/BeerOrderShipment';

export async function listShipments() {
  const { data } = await api.get<BeerOrderShipment[]>('/api/v1/shipments');
  return data;
}

export async function getShipmentById(shipmentId: number) {
  const { data } = await api.get<BeerOrderShipment>(`/api/v1/shipments/${shipmentId}`);
  return data;
}

export async function createShipment(payload: BeerOrderShipment) {
  const { data } = await api.post<BeerOrderShipment>('/api/v1/shipments', payload);
  return data;
}

export async function updateShipment(shipmentId: number, payload: BeerOrderShipment) {
  await api.put<void>(`/api/v1/shipments/${shipmentId}`, payload);
}

export async function deleteShipment(shipmentId: number) {
  await api.delete<void>(`/api/v1/shipments/${shipmentId}`);
}
