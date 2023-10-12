import type { Meta, StoryObj } from '@storybook/react';

import CommentForm from './CommentForm';

const meta: Meta<typeof CommentForm> = {
  title: 'recipe/CommentForm',
  component: CommentForm,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
