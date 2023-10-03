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
      showToast('토스트메세지');
    };

    return (
      <div style={{ width: '375px' }}>
        <>
          <button onClick={handleClick}>토스트 테스트</button>
          {isOpen && <Toast message="토스트 메세지" />}
        </>
      </div>
    );
  },
};

export const Error: Story = {
  render: () => {
    const { isOpen, showToast } = useToast();

    const handleClick = () => {
      showToast('토스트메세지');
    };

    return (
      <>
        <div style={{ width: '375px' }}>
          <button onClick={handleClick}>토스트 테스트</button>
          {isOpen && <Toast isError message="토스트 메세지" />}
        </div>
      </>
    );
  },
};
