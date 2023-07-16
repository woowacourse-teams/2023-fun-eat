import type { Meta, StoryObj } from '@storybook/react';

import RankingReviewItem from './RankingReviewItem';

const meta: Meta<typeof RankingReviewItem> = {
  title: 'review/RankingReviewItem',
  component: RankingReviewItem,
  args: {
    rankingReview: {
      reviewId: 1,
      productId: 5,
      productName: '구운감자슬림명란마요',
      content: '할머니가 먹을 거 같은 맛입니다.',
      rating: 4.0,
      favoriteCount: 1256,
    },
  },
};

export default meta;
type Story = StoryObj<typeof RankingReviewItem>;

export const Default: Story = {};
