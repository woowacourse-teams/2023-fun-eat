import type { Meta, StoryObj } from '@storybook/react';

import CategoryList from './CategoryList';

const meta: Meta<typeof CategoryList> = {
  title: 'common/CategoryList',
  component: CategoryList,
};

export default meta;
type Story = StoryObj<typeof CategoryList>;

export const Default: Story = {};
