/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type BeerOrderLine = {
    /**
     * Unique identifier for this line item.
     */
    readonly id?: number;
    /**
     * Version number for optimistic locking.
     */
    readonly version?: number;
    /**
     * Identifier of the beer being ordered.
     */
    beerId?: number;
    /**
     * Quantity of the beer requested in this line item.
     */
    orderQuantity?: number;
    /**
     * Quantity that has been allocated/fulfilled for this line item.
     */
    readonly quantityAllocated?: number;
    /**
     * Status of this line item.
     */
    status?: BeerOrderLine.status;
    /**
     * The date and time when the line item was created.
     */
    readonly createdDate?: string;
    /**
     * The date and time when the line item was last updated.
     */
    readonly updateDate?: string;
};
export namespace BeerOrderLine {
    /**
     * Status of this line item.
     */
    export enum status {
        PENDING = 'PENDING',
        DELIVERED = 'DELIVERED',
    }
}

