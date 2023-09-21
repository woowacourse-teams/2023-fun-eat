import type { Meta, StoryObj } from '@storybook/react';

import CategoryFoodTab from './CategoryFoodTab';

import CategoryProvider from '@/contexts/CategoryContext';

const meta: Meta<typeof CategoryFoodTab> = {
  title: 'common/CategoryFoodTab',
  component: CategoryFoodTab,
  args: {
    category: 'food',
  },
  decorators: [
    (Story) => (
      <CategoryProvider>
        <Story />
      </CategoryProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof CategoryFoodTab>;

export const Default: Story = {};
