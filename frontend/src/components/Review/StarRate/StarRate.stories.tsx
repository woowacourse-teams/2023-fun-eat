import type { Meta, StoryObj } from '@storybook/react';

import StarRate from './StarRate';

import ReviewFormProvider from '@/contexts/ReviewFormContext';

const meta: Meta<typeof StarRate> = {
  title: 'review/StarRate',
  component: StarRate,
  decorators: [
    (Story) => (
      <ReviewFormProvider>
        <Story />
      </ReviewFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof StarRate>;

export const Default: Story = {};
