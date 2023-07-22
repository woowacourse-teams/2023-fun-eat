import { rest } from 'msw';

import { isProductSortOption, isSortOrder } from './utils';
import foodCategory from '../data/foodCategory.json';
import pbCategory from '../data/pbCategory.json';
import productDetail from '../data/productDetail.json';
import categoryProducts from '../data/products.json';

export const productHandlers = [
  rest.get('/api/categories', (req, res, ctx) => {
    const categoryType = req.url.searchParams.get('type');

    if (categoryType === 'food') {
      return res(ctx.status(200), ctx.json(foodCategory));
    }

    if (categoryType === 'pb') {
      return res(ctx.status(200), ctx.json(pbCategory));
    }

    return res(ctx.status(400));
  }),

  rest.get('/api/categories/:categoryId/products', (req, res, ctx) => {
    const sortOptions = req.url.searchParams.get('sort');

    if (sortOptions === null) {
      return res(ctx.status(400));
    }

    const [key, sortOrder] = sortOptions.split(',');

    if (!isProductSortOption(key) || !isSortOrder(sortOrder)) {
      return res(ctx.status(400));
    }

    const sortedProducts = {
      ...categoryProducts,
      products: [...categoryProducts.products].sort((cur, next) =>
        sortOrder === 'asc' ? cur[key] - next[key] : next[key] - cur[key]
      ),
    };

    return res(ctx.status(200), ctx.json(sortedProducts));
  }),

  rest.get('/api/products/:productId', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(productDetail));
  }),
];
