import type { Meta, StoryObj } from '@storybook/react';

import NavigationBar from './NavigationBar';

const meta: Meta<typeof NavigationBar> = {
  title: 'common/NavigationBar',
  component: NavigationBar,
};

export default meta;
type Story = StoryObj<typeof NavigationBar>;

export const Default: Story = {
  render: () => (
    <div style={{ width: '375px' }}>
      <NavigationBar />
    </div>
  ),
};
