import type { Meta, StoryObj } from '@storybook/react';

import ReviewItem from './ReviewItem';

import reviews from '@/mocks/data/reviews.json';

const meta: Meta<typeof ReviewItem> = {
  title: 'review/ReviewItem',
  component: ReviewItem,
  args: {
    product: '꼬북칩',
  },
};

export default meta;
type Story = StoryObj<typeof ReviewItem>;

export const Default: Story = {
  render: ({ product }) => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ReviewItem product={product} review={reviews[0]} />
    </div>
  ),
};

export const RebuyAndFavorite: Story = {
  render: ({ product }) => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ReviewItem product={product} review={reviews[1]} />
    </div>
  ),
};
