import { setupWorker } from 'msw';

import { productHandlers, reviewHandlers } from './handlers';

export const worker = setupWorker(...productHandlers, ...reviewHandlers);
