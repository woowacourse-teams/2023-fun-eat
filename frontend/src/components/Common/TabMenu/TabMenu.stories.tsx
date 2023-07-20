import type { Meta, StoryObj } from '@storybook/react';

import TabMenu from './TabMenu';

const meta: Meta<typeof TabMenu> = {
  title: 'common/TabMenu',
  component: TabMenu,
  args: {
    tabMenus: ['리뷰 1,200', '꿀조합'],
  },
};

export default meta;
type Story = StoryObj<typeof TabMenu>;

export const Default: Story = {
  render: ({ ...args }) => (
    <div style={{ width: '375px', padding: '0 20px' }}>
      <TabMenu {...args} />
    </div>
  ),
};
