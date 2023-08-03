import { setupWorker } from 'msw';

import { productHandlers, reviewHandlers, loginHandlers, rankingHandlers, memberHandlers } from './handlers';

export const worker = setupWorker(
  ...productHandlers,
  ...reviewHandlers,
  ...loginHandlers,
  ...rankingHandlers,
  ...memberHandlers
);
