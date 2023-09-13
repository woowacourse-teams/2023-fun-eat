import type { Meta, StoryObj } from '@storybook/react';

import MemberReviewList from './MemberReviewList';

const meta: Meta<typeof MemberReviewList> = {
  title: 'members/MemberReviewList',
  component: MemberReviewList,
};

export default meta;
type Story = StoryObj<typeof MemberReviewList>;

export const Default: Story = {};
