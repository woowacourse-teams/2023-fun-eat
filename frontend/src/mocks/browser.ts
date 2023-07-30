import { setupWorker } from 'msw';

import { productHandlers, reviewHandlers, authHandlers } from './handlers';

export const worker = setupWorker(...productHandlers, ...reviewHandlers, ...authHandlers);
