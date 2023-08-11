import type { Meta, StoryObj } from '@storybook/react';

import Loading from './Loading';

const meta: Meta<typeof Loading> = {
  title: 'common/Loading',
  component: Loading,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
