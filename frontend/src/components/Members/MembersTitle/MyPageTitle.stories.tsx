import type { Meta, StoryObj } from '@storybook/react';

import MembersTitle from './MembersTitle';

import member from '@/mocks/data/members.json';

const meta: Meta<typeof MembersTitle> = {
  title: 'Members/MembersTitle',
  component: MembersTitle,
  args: {
    member: member,
  },
};

export default meta;
type Story = StoryObj<typeof MembersTitle>;

export const Default: Story = {};
