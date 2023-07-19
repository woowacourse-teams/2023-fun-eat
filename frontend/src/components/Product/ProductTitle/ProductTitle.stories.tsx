import type { Meta, StoryObj } from '@storybook/react';

import ProductTitle from './ProductTitle';

const meta: Meta<typeof ProductTitle> = {
  title: 'product/ProductTitle',
  component: ProductTitle,
};

export default meta;
type Story = StoryObj<typeof ProductTitle>;

export const Default: Story = {};
