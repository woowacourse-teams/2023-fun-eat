import type { Meta, StoryObj } from '@storybook/react';

import PBProductList from './PBProductList';

const meta: Meta<typeof PBProductList> = {
  title: 'product/PBProductList',
  component: PBProductList,
};

export default meta;
type Story = StoryObj<typeof PBProductList>;

export const Default: Story = {};
