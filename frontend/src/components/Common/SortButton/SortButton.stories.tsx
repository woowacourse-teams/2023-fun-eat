import type { Meta, StoryObj } from '@storybook/react';

import SortButton from './SortButton';

const meta: Meta<typeof SortButton> = {
  title: 'common/SortButton',
  component: SortButton,
  args: {
    option: '높은 가격순',
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
