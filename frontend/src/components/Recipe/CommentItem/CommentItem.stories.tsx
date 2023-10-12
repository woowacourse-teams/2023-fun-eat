import type { Meta, StoryObj } from '@storybook/react';

import CommentItem from './CommentItem';

import comments from '@/mocks/data/comments.json';

const meta: Meta<typeof CommentItem> = {
  title: 'recipe/CommentItem',
  component: CommentItem,
  args: {
    recipeComment: comments.comments[0],
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
