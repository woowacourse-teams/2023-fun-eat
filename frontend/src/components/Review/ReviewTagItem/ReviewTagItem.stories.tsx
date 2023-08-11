import type { Meta, StoryObj } from '@storybook/react';

import ReviewTagItem from './ReviewTagItem';

import ReviewFormProvider from '@/contexts/ReviewFormContext';

const meta: Meta<typeof ReviewTagItem> = {
  title: 'review/ReviewTagItem',
  component: ReviewTagItem,
  args: {
    id: 0,
    name: '단짠단짠',
    isSelected: false,
  },
  decorators: [
    (Story) => (
      <ReviewFormProvider>
        <Story />
      </ReviewFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof ReviewTagItem>;

export const Default: Story = {};

export const Selected: Story = {
  args: {
    isSelected: true,
  },
};
