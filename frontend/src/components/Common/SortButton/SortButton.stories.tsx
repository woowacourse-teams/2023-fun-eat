import type { Meta, StoryObj } from '@storybook/react';

import SortButton from './SortButton';

import { PRODUCT_SORT_OPTIONS } from '@/constants';

const meta: Meta<typeof SortButton> = {
  title: 'common/SortButton',
  component: SortButton,
  args: {
    option: PRODUCT_SORT_OPTIONS[0],
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
