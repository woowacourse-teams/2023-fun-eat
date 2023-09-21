import type { Meta, StoryObj } from '@storybook/react';

import CategoryFoodList from './CategoryFoodList';

const meta: Meta<typeof CategoryFoodList> = {
  title: 'common/CategoryFoodList',
  component: CategoryFoodList,
};

export default meta;
type Story = StoryObj<typeof CategoryFoodList>;

export const Default: Story = {};
