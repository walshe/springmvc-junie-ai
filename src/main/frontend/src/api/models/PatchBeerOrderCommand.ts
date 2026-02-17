/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type PatchBeerOrderCommand = {
    /**
     * Client-provided reference identifier for the order (e.g., purchase order number).
     */
    customerRef?: string;
    /**
     * Total payment amount in USD for the entire order.
     */
    paymentAmount?: number;
    /**
     * Overall status of the order.
     */
    status?: PatchBeerOrderCommand.status;
    /**
     * Ad hoc notes about the order.
     */
    notes?: string;
};
export namespace PatchBeerOrderCommand {
    /**
     * Overall status of the order.
     */
    export enum status {
        PENDING = 'PENDING',
        DELIVERED = 'DELIVERED',
    }
}

