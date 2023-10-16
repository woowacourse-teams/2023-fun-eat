import type { Meta, StoryObj } from '@storybook/react';

import MemberReviewItem from './MemberReviewItem';

const meta: Meta<typeof MemberReviewItem> = {
  title: 'members/MemberReviewItem',
  component: MemberReviewItem,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
