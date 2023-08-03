import { rest } from 'msw';

import { isProductSortOption, isSortOrder } from './utils';
import foodCategory from '../data/foodCategory.json';
import productDetails from '../data/productDetails.json';
import categoryProducts from '../data/products.json';
import storeCategory from '../data/storeCategory.json';

export const productHandlers = [
  rest.get('/api/categories', (req, res, ctx) => {
    const categoryType = req.url.searchParams.get('type');

    if (categoryType === 'food') {
      return res(ctx.status(200), ctx.json(foodCategory));
    }

    if (categoryType === 'store') {
      return res(ctx.status(200), ctx.json(storeCategory));
    }

    return res(ctx.status(400));
  }),

  rest.get('/api/categories/:categoryId/products', (req, res, ctx) => {
    const sortOptions = req.url.searchParams.get('sort');
    const page = Number(req.url.searchParams.get('page'));

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

    return res(
      ctx.status(200),
      ctx.json({ page: sortedProducts.page, products: sortedProducts.products.slice(0, (page + 1) * 5) }),
      ctx.delay(500)
    );
  }),

  rest.get('/api/products/:productId', (req, res, ctx) => {
    const { productId } = req.params;

    const isProductIdValid = categoryProducts.products.some(({ id }) => id === Number(productId));

    if (!isProductIdValid) {
      return res(ctx.status(400));
    }

    const targetProduct = productDetails.find(({ id }) => id === Number(productId));

    if (!targetProduct) {
      return res(ctx.status(400));
    }

    return res(ctx.status(200), ctx.json(targetProduct));
  }),
];
