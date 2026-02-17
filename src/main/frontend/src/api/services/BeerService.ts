/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Beer } from '../models/Beer';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class BeerService {
    /**
     * List beers (paged)
     * Returns a paginated list of beers. Optionally filter by beerName (case-insensitive contains).
     * @param page Page number starting from 1.
     * @param size Page size (max 1000).
     * @param beerName Optional case-insensitive substring to filter by beer name.
     * @param beerStyle Optional case-insensitive substring to filter by beer style (e.g. LAGER, IPA, etc.).
     * @returns any A page of beers.
     * @throws ApiError
     */
    public static listBeers(
        page: number,
        size: number,
        beerName?: string,
        beerStyle?: string,
    ): CancelablePromise<{
        content?: Array<Beer>;
        totalElements?: number;
        totalPages?: number;
        size?: number;
        number?: number;
        first?: boolean;
        last?: boolean;
        empty?: boolean;
    }> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/beer',
            query: {
                'page': page,
                'size': size,
                'beerName': beerName,
                'beerStyle': beerStyle,
            },
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Create a new beer
     * Creates a new beer record in the system.
     * @param requestBody
     * @returns Beer Beer created.
     * @throws ApiError
     */
    public static handlePost(
        requestBody: Beer,
    ): CancelablePromise<Beer> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/beer',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Get beer by ID
     * Returns a single beer record based on its unique identifier.
     * @param beerId The unique identifier of the beer.
     * @returns Beer The requested beer.
     * @throws ApiError
     */
    public static getBeerById(
        beerId: number,
    ): CancelablePromise<Beer> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/beer/{beerId}',
            path: {
                'beerId': beerId,
            },
            errors: {
                404: `Problem`,
            },
        });
    }
    /**
     * Update an existing beer
     * Updates an existing beer record based on its unique identifier.
     * @param beerId The unique identifier of the beer to update.
     * @param requestBody
     * @returns void
     * @throws ApiError
     */
    public static updateById(
        beerId: number,
        requestBody: Beer,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/beer/{beerId}',
            path: {
                'beerId': beerId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                404: `Problem`,
            },
        });
    }
    /**
     * Delete a beer
     * Deletes a beer record based on its unique identifier.
     * @param beerId The unique identifier of the beer to delete.
     * @returns void
     * @throws ApiError
     */
    public static deleteById(
        beerId: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/beer/{beerId}',
            path: {
                'beerId': beerId,
            },
            errors: {
                404: `Problem`,
            },
        });
    }
}
