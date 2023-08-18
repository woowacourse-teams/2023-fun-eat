import type { Meta, StoryObj } from '@storybook/react';

import NavigableSectionTitle from './NavigableSectionTitle';

const meta: Meta<typeof NavigableSectionTitle> = {
  title: 'common/NavigableSectionTitle',
  component: NavigableSectionTitle,
  args: {
    title: '내가 작성한 리뷰 (12개)',
  },
};

export default meta;
type Story = StoryObj<typeof NavigableSectionTitle>;

export const Default: Story = {};
