import type { Meta, StoryObj } from '@storybook/react';

import Carousel from './Carousel';

import { RecipeItem } from '@/components/Recipe';
import mockRecipe from '@/mocks/data/recipes.json';

const meta: Meta<typeof Carousel> = {
  title: 'common/Carousel',
  component: Carousel,
};

export default meta;
type Story = StoryObj<typeof Carousel>;

export const Default: Story = {
  args: {
    carouselList: [
      {
        id: 0,
        children: <div>1</div>,
      },
      {
        id: 1,
        children: <div>2</div>,
      },
      {
        id: 2,
        children: <div>3</div>,
      },
    ],
  },
};

export const RecipeRanking: Story = {
  args: {
    carouselList: [
      {
        id: 0,
        children: <RecipeItem recipe={mockRecipe.recipes[0]} />,
      },
      {
        id: 1,
        children: <RecipeItem recipe={mockRecipe.recipes[1]} />,
      },
      {
        id: 2,
        children: <RecipeItem recipe={mockRecipe.recipes[2]} />,
      },
    ],
  },
};
