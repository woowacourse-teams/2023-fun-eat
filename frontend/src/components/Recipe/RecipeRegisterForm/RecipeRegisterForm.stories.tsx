import type { Meta, StoryObj } from '@storybook/react';

import RecipeRegisterForm from './RecipeRegisterForm';

import RecipeFormProvider from '@/contexts/RecipeFormContext';

const meta: Meta<typeof RecipeRegisterForm> = {
  title: 'recipe/RecipeRegisterForm',
  component: RecipeRegisterForm,
  decorators: [
    (Story) => (
      <RecipeFormProvider>
        <Story />
      </RecipeFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof RecipeRegisterForm>;

export const Default: Story = {};
