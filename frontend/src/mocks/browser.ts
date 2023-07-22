import { setupWorker } from 'msw';

import { productHandlers } from './handlers';

export const worker = setupWorker(...productHandlers);
