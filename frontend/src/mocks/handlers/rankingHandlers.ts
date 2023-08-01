import { rest } from 'msw';

import mockReviewRankingList from '../data/reviewRankingList.json';

export const rankingHandlers = [
  rest.get('/api/ranks/reviews', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockReviewRankingList));
  }),
];
