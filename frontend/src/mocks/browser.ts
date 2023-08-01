import { setupWorker } from 'msw';

import { productHandlers, reviewHandlers, loginHandlers, rankingHandlers } from './handlers';

export const worker = setupWorker(...productHandlers, ...reviewHandlers, ...loginHandlers, ...rankingHandlers);
