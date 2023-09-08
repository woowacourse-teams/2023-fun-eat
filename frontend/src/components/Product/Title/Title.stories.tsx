import type { Meta, StoryObj } from '@storybook/react';

import Title from './Title';

const meta: Meta<typeof Title> = {
  title: 'common/Title',
  component: Title,
  args: {
    headingTitle: '상품 목록',
  },
};

export default meta;
type Story = StoryObj<typeof Title>;

export const Default: Story = {};
