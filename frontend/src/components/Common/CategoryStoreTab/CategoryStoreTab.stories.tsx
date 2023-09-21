import type { Meta, StoryObj } from '@storybook/react';

import CategoryStoreTab from './CategoryStoreTab';

import CategoryProvider from '@/contexts/CategoryContext';

const meta: Meta<typeof CategoryStoreTab> = {
  title: 'common/CategoryStoreTab',
  component: CategoryStoreTab,
  args: {
    category: 'store',
  },
  decorators: [
    (Story) => (
      <CategoryProvider>
        <Story />
      </CategoryProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof CategoryStoreTab>;

export const Default: Story = {};
