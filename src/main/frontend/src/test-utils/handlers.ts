import { http, HttpResponse } from 'msw';

export const handlers = [
  // Match any origin to handle absolute baseURLs used by Axios
  http.get('*/api/v1/beer', ({ request }) => {
    const url = new URL(request.url);
    // Optionally, inspect url.searchParams for page/size/etc.
    return HttpResponse.json({
      content: [
        { id: '1', beerName: 'Test Beer', beerStyle: 'IPA', upc: '123', quantityOnHand: 10, price: 5.99 }
      ],
      pageable: { pageNumber: 0, pageSize: 10 },
      totalPages: 1,
      totalElements: 1
    });
  }),
];
