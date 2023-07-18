import type { Meta, StoryObj } from '@storybook/react';

import TagList from './TagList';

import productDetail from '@/mocks/data/productDetail.json';

const meta: Meta<typeof TagList> = {
  title: 'common/TagList',
  component: TagList,
  args: {
    tags: productDetail.tags,
  },
};

export default meta;
type Story = StoryObj<typeof TagList>;

export const Default: Story = {};
