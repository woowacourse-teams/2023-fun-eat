import type { Meta, StoryObj } from '@storybook/react';

import RecipeUsedProducts from './RecipeUsedProducts';

const meta: Meta<typeof RecipeUsedProducts> = {
  title: 'recipe/RecipeUsedProducts',
  component: RecipeUsedProducts,
};

export default meta;
type Story = StoryObj<typeof RecipeUsedProducts>;

export const Default: Story = {};
