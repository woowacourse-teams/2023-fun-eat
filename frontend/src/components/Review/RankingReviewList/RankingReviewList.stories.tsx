import type { Meta, StoryObj } from '@storybook/react';

import RankingReviewList from './RankingReviewList';

const meta: Meta<typeof RankingReviewList> = {
  title: 'review/RankingReviewList',
  component: RankingReviewList,
};

export default meta;
type Story = StoryObj<typeof RankingReviewList>;

export const Default: Story = {};
