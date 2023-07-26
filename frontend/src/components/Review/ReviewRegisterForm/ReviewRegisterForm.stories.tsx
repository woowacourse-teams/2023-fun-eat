import type { Meta, StoryObj } from '@storybook/react';

import ReviewRegisterForm from './ReviewRegisterForm';

const meta: Meta<typeof ReviewRegisterForm> = {
  title: 'review/ReviewRegisterForm',
  component: ReviewRegisterForm,
};

export default meta;
type Story = StoryObj<typeof ReviewRegisterForm>;

export const Default: Story = {};
