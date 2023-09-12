import type { Meta, StoryObj } from '@storybook/react';

import Toast from './Toast';

import { useToast } from '@/hooks/common';

const meta: Meta<typeof Toast> = {
  title: 'common/Toast',
  component: Toast,
  args: {
    message: '꿀조합 작성하러 가기 🍯',
  },
};

export default meta;
type Story = StoryObj<typeof Toast>;

export const Default: Story = {
  render: () => {
    const { isOpen, showToast } = useToast();

    const handleClick = () => {
      showToast();
    };

    return (
      <>
        <div style={{ width: '375px' }}>
          <button onClick={handleClick}>토스트 테스트</button>
          {isOpen && <Toast isOpen={isOpen} message="토스트 메세지" />}
        </div>
      </>
    );
  },
};