import { rest } from 'msw';

import mockProductRankingList from '../data/productRankingList.json';
import mockReviewRankingList from '../data/reviewRankingList.json';

export const rankingHandlers = [
  rest.get('/api/ranks/products', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockProductRankingList));
  }),
  rest.get('/api/ranks/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockReviewRankingList));
  }),
];
