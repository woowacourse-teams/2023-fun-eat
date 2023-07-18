import type { Meta, StoryObj } from '@storybook/react';

import RankingProductItem from './RankingProductItem';

const meta: Meta<typeof RankingProductItem> = {
  title: 'product/RankingProductItem',
  component: RankingProductItem,
  args: {
    rankingProduct: {
      id: 3,
      rank: 1,
      image: 'https://t3.ftcdn.net/jpg/06/06/91/70/240_F_606917032_4ujrrMV8nspZDX8nTgGrTpJ69N9JNxOL.jpg',
      name: '소금빵',
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
