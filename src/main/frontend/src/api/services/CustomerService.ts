/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateCustomerCommand } from '../models/CreateCustomerCommand';
import type { Customer } from '../models/Customer';
import type { UpdateCustomerCommand } from '../models/UpdateCustomerCommand';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class CustomerService {
    /**
     * List Customers
     * @returns Customer A list of customers
     * @throws ApiError
     */
    public static listCustomers(): CancelablePromise<Array<Customer>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/customers',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Create Customer
     * @param requestBody
     * @returns Customer Customer created
     * @throws ApiError
     */
    public static createCustomer(
        requestBody: CreateCustomerCommand,
    ): CancelablePromise<Customer> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/customers',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
            },
        });
    }
    /**
     * Get Customer by ID
     * @param customerId
     * @returns Customer Customer found
     * @throws ApiError
     */
    public static getCustomerById(
        customerId: number,
    ): CancelablePromise<Customer> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/customers/{customerId}',
            path: {
                'customerId': customerId,
            },
            errors: {
                400: `Problem`,
                404: `Customer not found`,
            },
        });
    }
    /**
     * Update Customer
     * @param customerId
     * @param requestBody
     * @returns void
     * @throws ApiError
     */
    public static updateCustomer(
        customerId: number,
        requestBody: UpdateCustomerCommand,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/customers/{customerId}',
            path: {
                'customerId': customerId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Problem`,
                404: `Customer not found`,
            },
        });
    }
    /**
     * Delete Customer
     * @param customerId
     * @returns void
     * @throws ApiError
     */
    public static deleteCustomer(
        customerId: number,
    ): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/customers/{customerId}',
            path: {
                'customerId': customerId,
            },
            errors: {
                400: `Problem`,
                404: `Customer not found`,
            },
        });
    }
}
