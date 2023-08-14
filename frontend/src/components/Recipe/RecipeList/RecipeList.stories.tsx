import type { Meta, StoryObj } from '@storybook/react';

import RecipeList from './RecipeList';

const meta: Meta<typeof RecipeList> = {
  title: 'recipe/RecipeList',
  component: RecipeList,
};

export default meta;
type Story = StoryObj<typeof RecipeList>;

export const Default: Story = {};
