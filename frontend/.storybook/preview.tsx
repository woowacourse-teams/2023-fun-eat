import React from 'react';
import { FunEatProvider } from '@fun-eat/design-system';
import type { Preview } from '@storybook/react';
import { mswDecorator } from 'msw-storybook-addon';
import { handlers } from '../src/mocks/handlers';

export const decorators = [
  (Story) => (
    <FunEatProvider>
      <Story />
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
    msw: handlers,
  },
};

export default preview;
