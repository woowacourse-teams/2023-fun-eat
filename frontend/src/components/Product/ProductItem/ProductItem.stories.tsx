import type { Meta, StoryObj } from '@storybook/react';

import ProductItem from './ProductItem';

import mockProducts from '@/mocks/data/products.json';

const meta: Meta<typeof ProductItem> = {
  title: 'product/ProductItem',
  component: ProductItem,
  args: {
    product: mockProducts[0],
  },
};

export default meta;
type Story = StoryObj<typeof ProductItem>;

export const Default: Story = {};
