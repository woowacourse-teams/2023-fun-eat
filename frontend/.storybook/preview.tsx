import React from 'react';
import { FunEatProvider } from '@fun-eat/design-system';
import type { Preview } from '@storybook/react';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import {
  loginHandlers,
  productHandlers,
  reviewHandlers,
  rankingHandlers,
  memberHandlers,
  recipeHandlers,
  searchHandlers,
} from '../src/mocks/handlers';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

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
    msw: {
      handlers: [
        ...productHandlers,
        ...reviewHandlers,
        ...loginHandlers,
        ...rankingHandlers,
        ...memberHandlers,
        ...recipeHandlers,
        ...searchHandlers,
      ],
    },
  },
};

export default preview;
