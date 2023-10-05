import type { Meta, StoryObj } from '@storybook/react';

import Toast from './Toast';

import ToastProvider from '@/contexts/ToastContext';
import { useToastActionContext } from '@/hooks/context';

const meta: Meta<typeof Toast> = {
  title: 'common/Toast',
  component: Toast,
  decorators: [
    (Story) => (
      <ToastProvider>
        <Story />
      </ToastProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof Toast>;

export const Default: Story = {
  render: () => {
    const { toast } = useToastActionContext();
    const handleClick = () => {
      toast.success('성공');
    };
    return (
      <div style={{ width: '375px' }}>
        <>
          <button onClick={handleClick}>토스트 성공</button>
        </>
      </div>
    );
  },
};

export const Error: Story = {
  render: () => {
    const { toast } = useToastActionContext();
    const handleClick = () => {
      toast.error('실패');
    };
    return (
      <div style={{ width: '375px' }}>
        <>
          <button onClick={handleClick}>토스트 에러</button>
        </>
      </div>
    );
  },
};
