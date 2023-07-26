import type { Meta, StoryObj } from '@storybook/react';

import ReviewImageUploader from './ReviewImageUploader';

const meta: Meta<typeof ReviewImageUploader> = {
  title: 'review/ReviewImageUploader',
  component: ReviewImageUploader,
};

export default meta;
type Story = StoryObj<typeof ReviewImageUploader>;

export const Default: Story = {};
