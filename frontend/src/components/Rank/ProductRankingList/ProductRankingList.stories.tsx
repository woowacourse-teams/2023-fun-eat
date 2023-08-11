import type { Meta, StoryObj } from '@storybook/react';

import ProductRankingList from './ProductRankingList';
import mockProductRankingList from '../../../mocks/data/productRankingList.json';

const meta: Meta<typeof ProductRankingList> = {
  title: 'product/ProductRankingList',
  component: ProductRankingList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
