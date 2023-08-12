import type { Meta, StoryObj } from '@storybook/react';

import mockProfileReview from '@/mocks/data/profileReviews.json';
import ProfileReview from '@/pages/ProfileReviewPage';

const meta: Meta<typeof ProfileReview> = {
  title: 'Members/ProfileReview',
  component: ProfileReview,
  args: {
    member: mockProfileReview,
  },
};

export default meta;
type Story = StoryObj<typeof ProfileReview>;

export const Default: Story = {};
