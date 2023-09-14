import type { Meta, StoryObj } from '@storybook/react';

import ProductTitle from './ProductTitle';

const meta: Meta<typeof ProductTitle> = {
  title: 'common/ProductTitle',
  component: ProductTitle,
  args: {
    content: '상품 목록',
  },
};

export default meta;
type Story = StoryObj<typeof ProductTitle>;

export const Default: Story = {};
