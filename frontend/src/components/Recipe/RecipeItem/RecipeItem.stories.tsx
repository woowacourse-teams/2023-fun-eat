import type { Meta, StoryObj } from '@storybook/react';

import RecipeItem from './RecipeItem';

import mockRecipe from '@/mocks/data/recipes.json';

const meta: Meta<typeof RecipeItem> = {
  title: 'recipe/RecipeItem',
  component: RecipeItem,
  args: {
    recipe: mockRecipe.recipes[0],
  },
};

export default meta;
type Story = StoryObj<typeof RecipeItem>;

export const Default: Story = {};
