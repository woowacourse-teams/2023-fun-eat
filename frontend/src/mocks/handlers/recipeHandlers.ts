import { rest } from 'msw';

import recipeDetail from '../data/recipeDetail.json';

export const recipeHandlers = [
  rest.get('/api/recipes/:recipeId', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(recipeDetail));
  }),

  rest.post('/api/recipes', (req, res, ctx) => {
    const formData = req.body;

    if (!formData) {
      return res(ctx.status(400), ctx.json({ error: 'formData를 제공하지 않았습니다.' }));
    }

    return res(ctx.status(200), ctx.json({ message: '꿀조합이 등록되었습니다.' }), ctx.set('Location', '/recipes/1'));
  }),
];
