import type { Meta, StoryObj } from '@storybook/react';

import ProductItem from './ProductItem';
import type { Product } from '../mock';
import productImage from '../mock_img.jpg';

const MOCK_PRODUCT: Product = {
  id: 1,
  name: '꼬북칩',
  price: 1500,
  image: productImage,
  averageRating: 4.5,
  reviewCount: 100,
};

const meta: Meta<typeof ProductItem> = {
  title: 'product/ProductItem',
  component: ProductItem,
  args: {
    product: MOCK_PRODUCT,
  },
};

export default meta;
type Story = StoryObj<typeof ProductItem>;

export const Default: Story = {};
