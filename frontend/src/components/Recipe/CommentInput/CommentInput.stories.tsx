import type { Meta, StoryObj } from '@storybook/react';

import CommentInput from './CommentInput';

const meta: Meta<typeof CommentInput> = {
  title: 'recipe/CommentInput',
  component: CommentInput,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
