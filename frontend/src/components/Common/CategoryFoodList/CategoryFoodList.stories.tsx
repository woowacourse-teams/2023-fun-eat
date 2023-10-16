import type { Meta, StoryObj } from '@storybook/react';

import CategoryFoodList from './CategoryFoodList';

import CategoryProvider from '@/contexts/CategoryContext';

const meta: Meta<typeof CategoryFoodList> = {
  title: 'common/CategoryFoodList',
  component: CategoryFoodList,
  decorators: [
    (Story) => (
      <CategoryProvider>
        <Story />
      </CategoryProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof CategoryFoodList>;

export const Default: Story = {};
