import { rest } from 'msw';

import recipeDetail from '../data/recipeDetail.json';

export const recipeHandlers = [
  rest.get('/api/recipes/:recipeId', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(recipeDetail));
  }),
];
