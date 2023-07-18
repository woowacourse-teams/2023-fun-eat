import type { Meta, StoryObj } from '@storybook/react';

import ReviewRankingList from './ReviewRankingList';

const meta: Meta<typeof ReviewRankingList> = {
  title: 'review/ReviewRankingList',
  component: ReviewRankingList,
};

export default meta;
type Story = StoryObj<typeof ReviewRankingList>;

export const Default: Story = {};
