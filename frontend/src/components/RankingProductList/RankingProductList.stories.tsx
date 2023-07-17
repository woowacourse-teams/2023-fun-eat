import type { Meta, StoryObj } from '@storybook/react';

import RankingProductList from './RankingProductList';

const meta: Meta<typeof RankingProductList> = {
  title: 'product/RankingProductList',
  component: RankingProductList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
