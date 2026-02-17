/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type Beer = {
    /**
     * Unique identifier for the beer.
     */
    readonly id?: number;
    /**
     * Version number for optimistic locking.
     */
    readonly version?: number;
    /**
     * Name of the beer.
     */
    beerName: string;
    /**
     * Style of beer (e.g., ALE, PALE ALE, IPA, STOUT).
     */
    beerStyle: string;
    /**
     * Universal Product Code (UPC), a 13-digit number assigned to each unique beer.
     */
    upc: string;
    /**
     * The quantity of beer currently in stock.
     */
    quantityOnHand: number;
    /**
     * The price of the beer.
     */
    price: number;
    /**
     * The date and time when the beer record was created.
     */
    readonly createdDate?: string;
    /**
     * The date and time when the beer record was last updated.
     */
    readonly updateDate?: string;
};

