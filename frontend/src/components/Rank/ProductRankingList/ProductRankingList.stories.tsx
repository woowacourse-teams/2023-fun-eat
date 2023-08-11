import type { Meta, StoryObj } from '@storybook/react';

import ProductRankingList from './ProductRankingList';

const meta: Meta<typeof ProductRankingList> = {
  title: 'product/ProductRankingList',
  component: ProductRankingList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
