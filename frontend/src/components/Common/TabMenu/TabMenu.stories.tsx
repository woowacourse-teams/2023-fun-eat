import type { Meta, StoryObj } from '@storybook/react';

import TabMenu from './TabMenu';

const meta: Meta<typeof TabMenu> = {
  title: 'common/TabMenu',
  component: TabMenu,
};

export default meta;
type Story = StoryObj<typeof TabMenu>;

export const FoodCategory: Story = {
  args: {
    menuList: [
      { id: 0, name: '즉석조리' },
      { id: 1, name: '과자' },
      { id: 2, name: '간편식사' },
      { id: 3, name: '아이스크림' },
    ],
    menuVariant: 'food',
  },
};

export const StoreCategory: Story = {
  args: {
    menuList: [
      { id: 0, name: 'CU' },
      { id: 1, name: 'GS25' },
      { id: 2, name: '이마트24' },
    ],
    menuVariant: 'store',
  },
};
