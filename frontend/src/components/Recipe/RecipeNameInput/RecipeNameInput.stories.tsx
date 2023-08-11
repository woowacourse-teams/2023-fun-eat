import type { Meta, StoryObj } from '@storybook/react';

import RecipeNameInput from './RecipeNameInput';

const meta: Meta<typeof RecipeNameInput> = {
  title: 'recipe/RecipeNameInput',
  component: RecipeNameInput,
};

export default meta;
type Story = StoryObj<typeof RecipeNameInput>;

export const Default: Story = {};
