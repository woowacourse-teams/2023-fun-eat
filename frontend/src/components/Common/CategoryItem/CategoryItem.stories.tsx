import type { Meta, StoryObj } from '@storybook/react';

import CategoryItem from './CategoryItem';

const meta: Meta<typeof CategoryItem> = {
  title: 'common/CategoryItem',
  component: CategoryItem,
  args: {
    category: {
      id: 1,
      name: '즉석 식품',
      image: 'https://tqklhszfkvzk6518638.cdn.ntruss.com/product/8801771029052.jpg',
    },
  },
};

export default meta;
type Story = StoryObj<typeof CategoryItem>;

export const Default: Story = {};
