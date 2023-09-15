import type { Meta, StoryObj } from '@storybook/react';

import CategoryTab from './CategoryTab';

const meta: Meta<typeof CategoryTab> = {
  title: 'common/CategoryTab',
  component: CategoryTab,
};

export default meta;
type Story = StoryObj<typeof CategoryTab>;

export const FoodCategory: Story = {
  args: {
    menuVariant: 'food',
  },
};

export const StoreCategory: Story = {
  args: {
    menuVariant: 'store',
  },
};
