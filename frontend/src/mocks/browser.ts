import { setupWorker } from 'msw';

import { productHandlers, reviewHandlers, authHandlers, rankingHandlers } from './handlers';

export const worker = setupWorker(...productHandlers, ...reviewHandlers, ...authHandlers, ...rankingHandlers);
