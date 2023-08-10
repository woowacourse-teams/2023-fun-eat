import { rest } from 'msw';

import commonProducts from '../data/products.json';

export const loginHandlers = [
  rest.get('/api/search/:searchId', (req, res, ctx) => {
    const { searchId } = req.params;
    const query = req.url.searchParams.get('query');

    if (query === null) {
      return res(ctx.status(400));
    }

    if (searchId === 'products') {
      const filteredProducts = {
        page: { ...commonProducts.page },
        products: commonProducts.products.filter((product) => product.name.includes(query)),
      };
      return res(ctx.status(200), ctx.json(filteredProducts));
    }

    // TODO: 꿀조합 목 데이터 만들기
    if (searchId === 'recipes') {
      const filteredProducts = {
        page: { ...commonProducts.page },
        products: commonProducts.products.filter((product) => product.name.includes(query)),
      };
      return res(ctx.status(200), ctx.json(filteredProducts));
    }

    return res(ctx.status(400));
  }),
];
