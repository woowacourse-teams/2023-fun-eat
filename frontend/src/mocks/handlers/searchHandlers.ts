import { rest } from 'msw';

import recipeResponse from '../data/recipes.json';
import searchedProducts from '../data/searchedProducts.json';

export const searchHandlers = [
  rest.get('/api/search/:searchId/results', (req, res, ctx) => {
    const { searchId } = req.params;
    const query = req.url.searchParams.get('query');
    const page = Number(req.url.searchParams.get('page'));

    if (query === null) {
      return res(ctx.status(400));
    }

    if (searchId === 'products') {
      const filteredProducts = {
        page: { ...searchedProducts.page },
        products: searchedProducts.products
          .filter((product) => product.name.includes(query))
          .slice(page * 5, (page + 1) * 5),
      };
      return res(ctx.status(200), ctx.json(filteredProducts), ctx.delay(1000));
    }

    if (searchId === 'recipes') {
      const filteredRecipes = {
        page: { ...recipeResponse.page },
        products: recipeResponse.recipes.filter((recipe) =>
          recipe.products.some((product) => product.name.includes(query))
        ),
      };
      return res(ctx.status(200), ctx.json(filteredRecipes));
    }

    return res(ctx.status(400));
  }),
];
