import { describe, it, expect } from 'vitest';
import { listBeers } from '../api/beers';

describe('Beers API', () => {
  it('fetches a list of beers', async () => {
    const data = await listBeers({ page: 1, size: 10 });
    
    expect(data.content).toHaveLength(1);
    expect(data.content[0].beerName).toBe('Test Beer');
    expect(data.totalElements).toBe(1);
  });
});
