import type { Meta, StoryObj } from '@storybook/react';

import CategoryMenu from './CategoryMenu';

const meta: Meta<typeof CategoryMenu> = {
  title: 'common/CategoryMenu',
  component: CategoryMenu,
};

export default meta;
type Story = StoryObj<typeof CategoryMenu>;

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
