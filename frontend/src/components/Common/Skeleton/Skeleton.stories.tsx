import type { Meta, StoryObj } from '@storybook/react';

import Skeleton from './Skeleton';

const meta: Meta<typeof Skeleton> = {
  title: 'common/Skeleton',
  component: Skeleton,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    width: 100,
    height: 100,
  },
};
