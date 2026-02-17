/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateBeerOrderLineCommand } from './CreateBeerOrderLineCommand';
export type CreateBeerOrderCommand = {
    /**
     * Client-provided reference identifier for the order (e.g., purchase order number).
     */
    customerRef: string;
    /**
     * Ad hoc notes about the order.
     */
    notes?: string;
    /**
     * Line items for the order.
     */
    beerOrderLines: Array<CreateBeerOrderLineCommand>;
};

