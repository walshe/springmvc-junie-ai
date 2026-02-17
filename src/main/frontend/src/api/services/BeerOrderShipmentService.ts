/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BeerOrderShipment } from '../models/BeerOrderShipment';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class BeerOrderShipmentService {
    /**
     * List all shipments
     * Returns a list of all beer order shipments in the system.
     * @returns BeerOrderShipment A list of shipments.
     * @throws ApiError
     */
    public static listShipments(): CancelablePromise<Array<BeerOrderShipment>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/shipments',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Create a new shipment
     * Creates a new beer order shipment record in the system.
     * @param requestBody
     * @returns BeerOrderShipment Shipment created.
     * @throws ApiError
     */
    public static createShipment(
        requestBody: BeerOrderShipment,
    ): CancelablePromise<BeerOrderShipment> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/shipments',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Get shipment by ID
     * Returns a single beer order shipment record.
     * @param shipmentId
     * @returns BeerOrderShipment Shipment found.
     * @throws ApiError
     */
    public static getShipmentById(
        shipmentId: number,
    ): CancelablePromise<BeerOrderShipment> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/shipments/{shipmentId}',
            path: {
                'shipmentId': shipmentId,
            },
            errors: {
                400: `Problem`,
                404: `Shipment not found.`,
            },
        });
    }
    /**
     * Update a shipment
     * Updates an existing beer order shipment record.
     * @param shipmentId
     * @param requestBody
     * @returns void
     * @throws ApiError
     */
    public static updateShipment(
        shipmentId: number,
        requestBody: BeerOrderShipment,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/shipments/{shipmentId}',
            path: {
                'shipmentId': shipmentId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
                404: `Shipment not found.`,
            },
        });
    }
    /**
     * Delete a shipment
     * Deletes a beer order shipment record.
     * @param shipmentId
     * @returns void
     * @throws ApiError
     */
    public static deleteShipment(
        shipmentId: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/shipments/{shipmentId}',
            path: {
                'shipmentId': shipmentId,
            },
            errors: {
                400: `Problem`,
                404: `Shipment not found.`,
            },
        });
    }
}
