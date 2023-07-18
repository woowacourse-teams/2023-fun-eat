import type { Meta, StoryObj } from '@storybook/react';

import ReviewRankingItem from './ReviewRankingItem';

const meta: Meta<typeof ReviewRankingItem> = {
  title: 'review/ReviewRankingItem',
  component: ReviewRankingItem,
  args: {
    reviewRanking: {
      reviewId: 1,
      productId: 5,
      productName: '구운감자슬림명란마요',
      content:
        '할머니가 먹을 거 같은 맛입니다. 1960년 전쟁 때 맛 보고 싶었는데 그때는 너무 가난해서 먹을 수 없었는데요 이것보다 긴 리뷰도 잘려 보인답니다',
      rating: 4.0,
      favoriteCount: 1256,
    },
  },
};

export default meta;
type Story = StoryObj<typeof ReviewRankingItem>;

export const Default: Story = {};
