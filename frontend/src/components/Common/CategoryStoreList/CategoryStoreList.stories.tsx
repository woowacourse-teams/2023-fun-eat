import type { Meta, StoryObj } from '@storybook/react';

import CategoryStoreList from './CategoryStoreList';

const meta: Meta<typeof CategoryStoreList> = {
  title: 'common/CategoryStoreList',
  component: CategoryStoreList,
};

export default meta;
type Story = StoryObj<typeof CategoryStoreList>;

export const Default: Story = {};
