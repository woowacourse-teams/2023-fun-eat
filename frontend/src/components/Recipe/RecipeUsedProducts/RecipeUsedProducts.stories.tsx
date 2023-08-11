import type { Meta, StoryObj } from '@storybook/react';

import RecipeUsedProducts from './RecipeUsedProducts';

import RecipeFormProvider from '@/contexts/RecipeFormContext';

const meta: Meta<typeof RecipeUsedProducts> = {
  title: 'recipe/RecipeUsedProducts',
  component: RecipeUsedProducts,
  args: {
    usedProducts: [],
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
type Story = StoryObj<typeof RecipeUsedProducts>;

export const Default: Story = {};
