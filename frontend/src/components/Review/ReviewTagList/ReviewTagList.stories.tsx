import type { Meta, StoryObj } from '@storybook/react';

import ReviewTagList from './ReviewTagList';

const meta: Meta<typeof ReviewTagList> = {
  title: 'review/ReviewTagList',
  component: ReviewTagList,
};

export default meta;
type Story = StoryObj<typeof ReviewTagList>;

export const Default: Story = {};
