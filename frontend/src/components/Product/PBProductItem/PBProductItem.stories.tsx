import type { Meta, StoryObj } from '@storybook/react';

import PBProductItem from './PBProductItem';

import pbProducts from '@/mocks/data/pbProducts.json';

const meta: Meta<typeof PBProductItem> = {
  title: 'product/PBProductItem',
  component: PBProductItem,
  args: {
    pbProduct: pbProducts[0],
  },
};

export default meta;
type Story = StoryObj<typeof PBProductItem>;

export const Default: Story = {};
