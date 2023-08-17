import type { Meta, StoryObj } from '@storybook/react';

import RecipeRankingItem from './RecipeRankingItem';

import mockRecipeRankingList from '@/mocks/data/recipeRankingList.json';

const meta: Meta<typeof RecipeRankingItem> = {
  title: 'recipe/RecipeRankingItem',
  component: RecipeRankingItem,
  args: {
    rank: 1,
    recipe: mockRecipeRankingList.recipes[0],
  },
};

export default meta;
type Story = StoryObj<typeof RecipeRankingItem>;

export const Default: Story = {};
