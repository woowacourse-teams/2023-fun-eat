import type { Meta, StoryObj } from '@storybook/react';

import RecipeRankingList from './RecipeRankingList';

const meta: Meta<typeof RecipeRankingList> = {
  title: 'recipe/RecipeRankingList',
  component: RecipeRankingList,
};

export default meta;
type Story = StoryObj<typeof RecipeRankingList>;

export const Default: Story = {};
