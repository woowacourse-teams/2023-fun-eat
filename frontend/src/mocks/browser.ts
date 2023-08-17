import { setupWorker } from 'msw';

import {
  productHandlers,
  reviewHandlers,
  loginHandlers,
  rankingHandlers,
  memberHandlers,
  recipeHandlers,
  searchHandlers,
  logoutHandlers,
} from './handlers';

export const worker = setupWorker(
  ...productHandlers,
  ...reviewHandlers,
  ...loginHandlers,
  ...rankingHandlers,
  ...memberHandlers,
  ...recipeHandlers,
  ...searchHandlers,
  ...logoutHandlers
);
