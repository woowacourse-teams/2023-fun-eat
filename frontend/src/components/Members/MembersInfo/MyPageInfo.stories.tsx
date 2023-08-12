import type { Meta, StoryObj } from '@storybook/react';

import MembersInfo from './MembersInfo';

import mockMember from '@/mocks/data/members.json';

const meta: Meta<typeof MembersInfo> = {
  title: 'Members/MembersInfo',
  component: MembersInfo,
  args: {
    member: mockMember,
  },
};

export default meta;
type Story = StoryObj<typeof MembersInfo>;

export const Default: Story = {};
