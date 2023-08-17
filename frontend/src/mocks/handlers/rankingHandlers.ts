import { rest } from 'msw';

import mockProductRankingList from '../data/productRankingList.json';
import mockRecipeRankingList from '../data/recipeRankingList.json';
import mockReviewRankingList from '../data/reviewRankingList.json';

export const rankingHandlers = [
  rest.get('/api/ranks/products', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockProductRankingList), ctx.delay(1000));
  }),
  rest.get('/api/ranks/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockReviewRankingList), ctx.delay(1000));
  }),
  rest.get('/api/ranks/recipes', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockRecipeRankingList), ctx.delay(1000));
  }),
];
