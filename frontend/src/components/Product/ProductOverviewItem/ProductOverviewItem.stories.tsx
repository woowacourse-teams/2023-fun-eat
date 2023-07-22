import type { Meta, StoryObj } from '@storybook/react';

import ProductOverviewItem from './ProductOverviewItem';

const meta: Meta<typeof ProductOverviewItem> = {
  title: 'product/ProductOverviewItem',
  component: ProductOverviewItem,
  args: {
    productRanking: {
      id: 3,
      rank: 1,
      image: 'https://t3.ftcdn.net/jpg/06/06/91/70/240_F_606917032_4ujrrMV8nspZDX8nTgGrTpJ69N9JNxOL.jpg',
      name: '소금빵',
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    variant: 'default',
  },
};

export const Ranking: Story = {
  args: {
    variant: 'ranking',
  },
};
