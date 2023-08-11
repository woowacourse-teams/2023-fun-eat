import type { Meta, StoryObj } from '@storybook/react';

import ReviewTextarea from './ReviewTextarea';

import ReviewFormProvider from '@/contexts/ReviewFormContext';

const meta: Meta<typeof ReviewTextarea> = {
  title: 'review/ReviewTextarea',
  component: ReviewTextarea,
  args: {
    content: '',
  },
  decorators: [
    (Story) => (
      <ReviewFormProvider>
        <Story />
      </ReviewFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof ReviewTextarea>;

export const Default: Story = {};
