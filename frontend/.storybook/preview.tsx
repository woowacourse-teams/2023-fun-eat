import React from 'react';
import { FunEatProvider } from '@fun-eat/design-system';
import type { Preview } from '@storybook/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import { BrowserRouter } from 'react-router-dom';

import { productHandlers, reviewHandlers, loginHandlers, rankingHandlers, memberHandlers } from '../src/mocks/handlers';

initialize({
  serviceWorker: {
    url: '/mockServiceWorker.js',
  },
});

const queryClient = new QueryClient();

export const decorators = [
  (Story) => (
    <QueryClientProvider client={queryClient}>
      <FunEatProvider>
        <BrowserRouter>
          <Story />
        </BrowserRouter>
      </FunEatProvider>
    </QueryClientProvider>
  ),
  mswDecorator,
];

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    msw: { handlers: [...productHandlers, ...reviewHandlers, ...loginHandlers, ...rankingHandlers, ...memberHandlers] },
  },
};

export default preview;
