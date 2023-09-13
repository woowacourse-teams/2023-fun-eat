import type { Meta, StoryObj } from '@storybook/react';

import MemberRecipeList from './MemberRecipeList';

const meta: Meta<typeof MemberRecipeList> = {
  title: 'members/ MemberRecipeList',
  component: MemberRecipeList,
};

export default meta;
type Story = StoryObj<typeof MemberRecipeList>;

export const Default: Story = {};
