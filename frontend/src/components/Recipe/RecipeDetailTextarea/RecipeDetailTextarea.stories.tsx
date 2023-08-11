import type { Meta, StoryObj } from '@storybook/react';

import { RecipeDetailTextarea } from '..';

const meta: Meta<typeof RecipeDetailTextarea> = {
  title: 'recipe/RecipeDetailTextarea',
  component: RecipeDetailTextarea,
  args: {
    recipeDetail: '',
  },
};

export default meta;
type Story = StoryObj<typeof RecipeDetailTextarea>;

export const Default: Story = {};
