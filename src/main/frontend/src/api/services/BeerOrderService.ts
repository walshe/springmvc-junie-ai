/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BeerOrder } from '../models/BeerOrder';
import type { CreateBeerOrderCommand } from '../models/CreateBeerOrderCommand';
import type { PatchBeerOrderCommand } from '../models/PatchBeerOrderCommand';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class BeerOrderService {
    /**
     * List beer orders
     * Retrieve a list of beer orders.
     * @returns BeerOrder Successfully retrieved list of beer orders.
     * @throws ApiError
     */
    public static listBeerOrders(): CancelablePromise<Array<BeerOrder>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/beerOrder',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Create a beer order
     * Create a new beer order.
     * @param requestBody Command object containing the order details to create.
     * @returns BeerOrder Beer order created successfully.
     * @throws ApiError
     */
    public static createBeerOrder(
        requestBody: CreateBeerOrderCommand,
    ): CancelablePromise<BeerOrder> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/beerOrder',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Get beer order by ID
     * Retrieve a single beer order by its identifier.
     * @param orderId Identifier of the beer order.
     * @returns BeerOrder Successfully retrieved the beer order.
     * @throws ApiError
     */
    public static getBeerOrderById(
        orderId: number,
    ): CancelablePromise<BeerOrder> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/beerOrder/{orderId}',
            path: {
                'orderId': orderId,
            },
            errors: {
                404: `Problem`,
            },
        });
    }
    /**
     * Partial update beer order
     * Update specific fields of an existing beer order.
     * @param orderId Identifier of the beer order.
     * @param requestBody
     * @returns void
     * @throws ApiError
     */
    public static patchBeerOrder(
        orderId: number,
        requestBody: PatchBeerOrderCommand,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/v1/beerOrder/{orderId}',
            path: {
                'orderId': orderId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
                404: `Problem`,
            },
        });
    }
}
