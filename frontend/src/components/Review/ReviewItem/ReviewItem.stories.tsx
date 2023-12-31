import type { Meta, StoryObj } from '@storybook/react';

import ReviewItem from './ReviewItem';

import mockReviews from '@/mocks/data/reviews.json';

const meta: Meta<typeof ReviewItem> = {
  title: 'review/ReviewItem',
  component: ReviewItem,
};

export default meta;
type Story = StoryObj<typeof ReviewItem>;

export const Default: Story = {
  render: () => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ReviewItem productId={1} review={mockReviews.reviews[0]} />
    </div>
  ),
};

export const RebuyAndFavorite: Story = {
  render: () => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ReviewItem productId={1} review={mockReviews.reviews[1]} />
    </div>
  ),
};

export const NoImageReview: Story = {
  render: () => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ReviewItem productId={1} review={mockReviews.reviews[2]} />
    </div>
  ),
};
