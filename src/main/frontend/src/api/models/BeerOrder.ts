/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BeerOrderLine } from './BeerOrderLine';
export type BeerOrder = {
    /**
     * Unique identifier for the beer order.
     */
    readonly id?: number;
    /**
     * Version number for optimistic locking.
     */
    readonly version?: number;
    /**
     * Client-provided reference identifier for the order (e.g., purchase order number).
     */
    customerRef?: string;
    /**
     * Total payment amount in USD for the entire order.
     */
    readonly paymentAmount?: number;
    /**
     * Overall status of the order.
     */
    status?: BeerOrder.status;
    /**
     * Ad hoc notes about the order.
     */
    notes?: string;
    /**
     * Line items in this order.
     */
    beerOrderLines?: Array<BeerOrderLine>;
    /**
     * The date and time when the order was created.
     */
    readonly createdDate?: string;
    /**
     * The date and time when the order was last updated.
     */
    readonly updateDate?: string;
};
export namespace BeerOrder {
    /**
     * Overall status of the order.
     */
    export enum status {
        PENDING = 'PENDING',
        DELIVERED = 'DELIVERED',
    }
}

