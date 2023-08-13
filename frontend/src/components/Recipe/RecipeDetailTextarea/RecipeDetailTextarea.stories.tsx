import type { Meta, StoryObj } from '@storybook/react';

import { RecipeDetailTextarea } from '..';

import RecipeFormProvider from '@/contexts/RecipeFormContext';

const meta: Meta<typeof RecipeDetailTextarea> = {
  title: 'recipe/RecipeDetailTextarea',
  component: RecipeDetailTextarea,
  args: {
    recipeDetail: '',
  },
  decorators: [
    (Story) => (
      <RecipeFormProvider>
        <Story />
      </RecipeFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof RecipeDetailTextarea>;

export const Default: Story = {};
