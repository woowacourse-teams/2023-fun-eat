import type { Meta, StoryObj } from '@storybook/react';

import StarRate from './StarRate';

const meta: Meta<typeof StarRate> = {
  title: 'review/StarRate',
  component: StarRate,
};

export default meta;
type Story = StoryObj<typeof StarRate>;

export const Default: Story = {};
