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
    menuList: ['즉석조리', '과자', '간편식사', '아이스크림'],
    menuVariant: 'food',
  },
};

export const StoreCategory: Story = {
  args: {
    menuList: ['CU', 'GS25', '이마트24'],
    menuVariant: 'store',
  },
};
