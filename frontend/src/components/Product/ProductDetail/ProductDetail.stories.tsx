import type { Meta, StoryObj } from '@storybook/react';

import ProductDetail from './ProductDetail';

const meta: Meta<typeof ProductDetail> = {
  title: 'product/ProductDetail',
  component: ProductDetail,
};

export default meta;
type Story = StoryObj<typeof ProductDetail>;

export const Default: Story = {
  render: () => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <ProductDetail />
    </div>
  ),
};
