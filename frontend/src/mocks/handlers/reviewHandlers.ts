import { rest } from 'msw';

import { isReviewSortOption, isSortOrder } from './utils';
import mockReviewRanking from '../data/reviewRankingList.json';
import mockReviews from '../data/reviews.json';

import type { ReviewFavoriteRequestBody, ReviewPostRequestBody } from '@/types/review';

export const reviewHandlers = [
  rest.get('/api/products/:productId/reviews', (req, res, ctx) => {
    const sortOptions = req.url.searchParams.get('sort');

    if (sortOptions === null) {
      return res(ctx.status(400));
    }

    const [key, sortOrder] = sortOptions.split(',');

    if (!isReviewSortOption(key) || !isSortOrder(sortOrder)) {
      return res(ctx.status(400));
    }

    const sortedReviews = {
      ...mockReviews,
      reviews: [...mockReviews.reviews].sort((cur, next) =>
        sortOrder === 'asc' ? cur[key] - next[key] : next[key] - cur[key]
      ),
    };

    return res(ctx.status(200), ctx.json(sortedReviews));
  }),

  rest.post<ReviewPostRequestBody>('/api/products/:productId/reviews', (req, res, ctx) => {
    const formData = req.body;
    const {
      reviewRequest: { rating, content },
    } = formData;

    if (!rating || !content) {
      return res(ctx.status(400));
    }

    return res(ctx.status(201));
  }),

  rest.patch<ReviewFavoriteRequestBody>('/api/products/:productId/reviews/:reviewId', (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get('/api/ranks/review', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockReviewRanking));
  }),
];
