import type { Meta, StoryObj } from '@storybook/react';

import SectionTitle from './SectionTitleTitle';

const meta: Meta<typeof SectionTitle> = {
  title: 'common/SectionTitle',
  component: SectionTitle,
};

export default meta;
type Story = StoryObj<typeof SectionTitle>;

export const Default: Story = {
  args: {
    name: '사이다',
    bookmark: false,
  },
};

export const Bookmarked: Story = {
  args: {
    name: '사이다',
    bookmark: true,
  },
};
