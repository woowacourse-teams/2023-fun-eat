import type { Meta, StoryObj } from '@storybook/react';

import CategoryItem from './CategoryItem';

import CategoryProvider from '@/contexts/CategoryContext';

const meta: Meta<typeof CategoryItem> = {
  title: 'common/CategoryItem',
  component: CategoryItem,
  decorators: [
    (Story) => (
      <CategoryProvider>
        <Story />
      </CategoryProvider>
    ),
  ],
  args: {
    categoryId: 1,
    name: '즉석 식품',
    image: 'https://tqklhszfkvzk6518638.cdn.ntruss.com/product/8801771029052.jpg',
    categoryType: 'food',
  },
};

export default meta;
type Story = StoryObj<typeof CategoryItem>;

export const Default: Story = {};
