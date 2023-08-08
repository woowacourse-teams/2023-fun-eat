import type { Meta, StoryObj } from '@storybook/react';

import ScrollButton from './ScrollButton';

const meta: Meta<typeof ScrollButton> = {
  title: 'common/ScrollButton',
  component: ScrollButton,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
