import type { Meta, StoryObj } from '@storybook/react';

import ProductItem from './ProductItem';

const meta: Meta<typeof ProductItem> = {
  title: 'product/ProductItem',
  component: ProductItem,
};

export default meta;
type Story = StoryObj<typeof ProductItem>;

export const Default: Story = {};
