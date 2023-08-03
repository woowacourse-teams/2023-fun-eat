import type { Meta, StoryObj } from '@storybook/react';

import ReviewRankingList from './ReviewRankingList';
import mockReviewRankingList from '../../../mocks/data/reviewRankingList.json';

const meta: Meta<typeof ReviewRankingList> = {
  title: 'review/ReviewRankingList',
  component: ReviewRankingList,
  args: {
    reviewRankings: mockReviewRankingList.reviews,
  },
};

export default meta;
type Story = StoryObj<typeof ReviewRankingList>;

export const Default: Story = {};
