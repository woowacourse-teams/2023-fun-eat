import type { Meta, StoryObj } from '@storybook/react';

import MembersTitle from './MembersTitle';

const meta: Meta<typeof MembersTitle> = {
  title: 'members/MembersTitle',
  component: MembersTitle,
  args: {
    title: '내가 작성한 리뷰 (12개)',
  },
};

export default meta;
type Story = StoryObj<typeof MembersTitle>;

export const Default: Story = {};
