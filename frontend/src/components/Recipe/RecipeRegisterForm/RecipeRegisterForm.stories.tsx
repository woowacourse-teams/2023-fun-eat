import type { Meta, StoryObj } from '@storybook/react';

import RecipeRegisterForm from './RecipeRegisterForm';

const meta: Meta<typeof RecipeRegisterForm> = {
  title: 'recipe/RecipeRegisterForm',
  component: RecipeRegisterForm,
};

export default meta;
type Story = StoryObj<typeof RecipeRegisterForm>;

export const Default: Story = {};
