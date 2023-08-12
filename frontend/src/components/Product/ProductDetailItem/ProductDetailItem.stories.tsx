import type { Meta, StoryObj } from '@storybook/react';

import ProductDetailItem from './ProductDetailItem';

import productDetails from '@/mocks/data/productDetails.json';

const meta: Meta<typeof ProductDetailItem> = {
  title: 'product/ProductDetailItem',
  component: ProductDetailItem,
  args: {
    productId: productDetails[0].id,
  },
};

export default meta;
type Story = StoryObj<typeof ProductDetailItem>;

export const Default: Story = {
  render: ({ ...args }) => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ProductDetailItem {...args} />
    </div>
  ),
};
