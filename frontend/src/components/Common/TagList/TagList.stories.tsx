import type { Meta, StoryObj } from '@storybook/react';

import TagList from './TagList';

import productDetails from '@/mocks/data/productDetails.json';

const meta: Meta<typeof TagList> = {
  title: 'common/TagList',
  component: TagList,
  args: {
    tags: productDetails[0].tags,
  },
};

export default meta;
type Story = StoryObj<typeof TagList>;

export const Default: Story = {};
