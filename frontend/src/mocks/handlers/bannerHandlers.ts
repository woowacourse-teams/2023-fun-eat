import { rest } from 'msw';

import banners from '../data/banners.json';

export const bannerHandlers = [
  rest.get('/api/banners', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(banners));
  }),
];
