import type { Meta, StoryObj } from '@storybook/react';

import CategoryStoreList from './CategoryStoreList';

import CategoryProvider from '@/contexts/CategoryContext';

const meta: Meta<typeof CategoryStoreList> = {
  title: 'common/CategoryStoreList',
  component: CategoryStoreList,
  decorators: [
    (Story) => (
      <CategoryProvider>
        <Story />
      </CategoryProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof CategoryStoreList>;

export const Default: Story = {};
