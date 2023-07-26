import type { Meta, StoryObj } from '@storybook/react';

import ReviewTagItem from './ReviewTagItem';

const meta: Meta<typeof ReviewTagItem> = {
  title: 'review/ReviewTagItem',
  component: ReviewTagItem,
  args: {
    id: 0,
    content: '단짠단짠',
    isSelected: true,
    isDisabled: false,
  },
};

export default meta;
type Story = StoryObj<typeof ReviewTagItem>;

export const Default: Story = {};

export const Selected: Story = {
  args: {
    isSelected: true,
  },
};

export const Disabled: Story = {
  args: {
    isDisabled: true,
  },
};
