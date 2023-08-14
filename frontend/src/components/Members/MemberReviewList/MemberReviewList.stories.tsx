import type { Meta, StoryObj } from '@storybook/react';

import mockMemberReview from '@/mocks/data/profileReviews.json';
import MemberReview from '@/pages/MemberReviewPage';

const meta: Meta<typeof MemberReview> = {
  title: 'members/MemberReview',
  component: MemberReview,
  args: {
    member: mockMemberReview,
  },
};

export default meta;
type Story = StoryObj<typeof MemberReview>;

export const Default: Story = {};
