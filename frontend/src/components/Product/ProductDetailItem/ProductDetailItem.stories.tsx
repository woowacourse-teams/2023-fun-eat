import type { Meta, StoryObj } from '@storybook/react';

import ProductDetailItem from './ProductDetailItem';

import productDetail from '@/mocks/data/productDetail.json';

const meta: Meta<typeof ProductDetailItem> = {
  title: 'product/ProductDetailItem',
  component: ProductDetailItem,
  args: {
    product: productDetail,
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
