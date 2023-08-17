import { rest } from 'msw';

import { isRecipeSortOption, isSortOrder } from './utils';
import recipeDetail from '../data/recipeDetail.json';
import mockRecipes from '../data/recipes.json';

export const recipeHandlers = [
  rest.get('/api/recipes/:recipeId', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(recipeDetail), ctx.delay(1000));
  }),

  rest.post('/api/recipes', (req, res, ctx) => {
    const formData = req.body;

    if (!formData) {
      return res(ctx.status(400), ctx.json({ error: 'formData를 제공하지 않았습니다.' }));
    }

    return res(ctx.status(200), ctx.json({ message: '꿀조합이 등록되었습니다.' }), ctx.set('Location', '/recipes/1'));
  }),

  rest.patch('/api/recipes/:recipeId', (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get('/api/recipes', (req, res, ctx) => {
    const sortOptions = req.url.searchParams.get('sort');
    const page = Number(req.url.searchParams.get('page'));

    if (sortOptions === null) {
      return res(ctx.status(400));
    }

    const [key, sortOrder] = sortOptions.split(',');

    if (!isRecipeSortOption(key) || !isSortOrder(sortOrder)) {
      return res(ctx.status(400));
    }

    const sortedRecipes = {
      ...mockRecipes,
      recipes: [...mockRecipes.recipes].sort((cur, next) => {
        if (key === 'createdAt') {
          return sortOrder === 'asc'
            ? new Date(cur[key]).getTime() - new Date(next[key]).getTime()
            : new Date(next[key]).getTime() - new Date(cur[key]).getTime();
        }

        return sortOrder === 'asc' ? cur[key] - next[key] : next[key] - cur[key];
      }),
    };

    return res(
      ctx.status(200),
      ctx.json({ page: sortedRecipes.page, recipes: sortedRecipes.recipes.slice(page * 5, (page + 1) * 5) })
    );
  }),

  rest.get('/api/products/:productId/recipes', (req, res, ctx) => {
    const sortOptions = req.url.searchParams.get('sort');
    const page = Number(req.url.searchParams.get('page'));

    if (sortOptions === null) {
      return res(ctx.status(400));
    }

    const [key, sortOrder] = sortOptions.split(',');

    if (!isRecipeSortOption(key) || !isSortOrder(sortOrder)) {
      return res(ctx.status(400));
    }

    const sortedRecipes = {
      ...mockRecipes,
      recipes: [...mockRecipes.recipes].sort((cur, next) => {
        if (key === 'createdAt') {
          return sortOrder === 'asc'
            ? new Date(cur[key]).getTime() - new Date(next[key]).getTime()
            : new Date(next[key]).getTime() - new Date(cur[key]).getTime();
        }

        return sortOrder === 'asc' ? cur[key] - next[key] : next[key] - cur[key];
      }),
    };

    return res(
      ctx.status(200),
      ctx.json({ ...sortedRecipes, recipes: sortedRecipes.recipes.slice(page * 5, (page + 1) * 5) })
    );
  }),
];
