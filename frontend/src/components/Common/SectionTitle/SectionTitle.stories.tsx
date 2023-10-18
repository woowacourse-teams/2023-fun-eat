import type { Meta, StoryObj } from '@storybook/react';

import SectionTitle from './SectionTitle';

const meta: Meta<typeof SectionTitle> = {
  title: 'common/SectionTitle',
  component: SectionTitle,
};

export default meta;
type Story = StoryObj<typeof SectionTitle>;

export const Default: Story = {
  args: {
    name: '사이다',
  },
};

export const Bookmarked: Story = {
  args: {
    name: '사이다',
  },
};
