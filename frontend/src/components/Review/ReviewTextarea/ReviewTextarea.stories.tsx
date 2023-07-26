import type { Meta, StoryObj } from '@storybook/react';

import ReviewTextarea from './ReviewTextarea';

const meta: Meta<typeof ReviewTextarea> = {
  title: 'review/ReviewTextarea',
  component: ReviewTextarea,
};

export default meta;
type Story = StoryObj<typeof ReviewTextarea>;

export const Default: Story = {};
