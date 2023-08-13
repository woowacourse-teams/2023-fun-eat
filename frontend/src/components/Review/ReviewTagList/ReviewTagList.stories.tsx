import type { Meta, StoryObj } from '@storybook/react';

import ReviewTagList from './ReviewTagList';

import ReviewFormProvider from '@/contexts/ReviewFormContext';

const meta: Meta<typeof ReviewTagList> = {
  title: 'review/ReviewTagList',
  component: ReviewTagList,
  args: {
    selectedTags: [],
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
type Story = StoryObj<typeof ReviewTagList>;

export const Default: Story = {};
