import React from 'react';
import { FunEatProvider } from '@fun-eat/design-system';
import type { Preview } from '@storybook/react';
import { mswDecorator } from 'msw-storybook-addon';
import { authHandlers, productHandlers, reviewHandlers } from '../src/mocks/handlers';
import { BrowserRouter } from 'react-router-dom';

export const decorators = [
  (Story) => (
    <FunEatProvider>
      <BrowserRouter>
        <Story />
      </BrowserRouter>
    </FunEatProvider>
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
    msw: [...authHandlers, ...productHandlers, ...reviewHandlers],
  },
};

export default preview;
