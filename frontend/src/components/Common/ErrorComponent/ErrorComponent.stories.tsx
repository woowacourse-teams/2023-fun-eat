import type { Meta, StoryObj } from '@storybook/react';

import ErrorComponent from './ErrorComponent';

const meta: Meta<typeof ErrorComponent> = {
  title: 'common/ErrorComponent',
  component: ErrorComponent,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
