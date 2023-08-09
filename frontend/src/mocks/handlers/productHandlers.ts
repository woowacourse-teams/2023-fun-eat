import { rest } from 'msw';

import { isProductSortOption, isSortOrder } from './utils';
import foodCategory from '../data/foodCategory.json';
import pbProducts from '../data/pbProducts.json';
import productDetails from '../data/productDetails.json';
import commonProducts from '../data/products.json';
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
    const categoryId = req.params.categoryId;
    const page = Number(req.url.searchParams.get('page'));

    if (sortOptions === null) {
      return res(ctx.status(400));
    }

    if (typeof categoryId !== 'string') {
      return res(ctx.status(400));
    }

    let products = commonProducts;

    if (Number(categoryId) >= 7 && Number(categoryId) <= 9) {
      products = pbProducts;
    }

    const [key, sortOrder] = sortOptions.split(',');

    if (!isProductSortOption(key) || !isSortOrder(sortOrder)) {
      return res(ctx.status(400));
    }

    const sortedProducts = {
      ...products,
      products: [...products.products].sort((cur, next) =>
        sortOrder === 'asc' ? cur[key] - next[key] : next[key] - cur[key]
      ),
    };
    return res(
      ctx.status(200),
      ctx.json({ page: sortedProducts.page, products: products.products.slice(page * 5, (page + 1) * 5) }),
      ctx.delay(500)
    );
  }),

  rest.get('/api/products/:productId', (req, res, ctx) => {
    const { productId } = req.params;

    const isProductIdValid = commonProducts.products.some(({ id }) => id === Number(productId));

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
