import type { Meta, StoryObj } from '@storybook/react';

import MoreButton from './MoreButton';

const meta: Meta<typeof MoreButton> = {
  title: 'common/MoreButton',
  component: MoreButton,
};

export default meta;
type Story = StoryObj<typeof MoreButton>;

export const Default: Story = {};
