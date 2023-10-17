import type { Meta, StoryObj } from '@storybook/react';

import BestReviewItem from './BestReviewItem';

const meta: Meta<typeof BestReviewItem> = {
  title: 'review/BestReviewItem',
  component: BestReviewItem,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    productId: 2,
  },
};
