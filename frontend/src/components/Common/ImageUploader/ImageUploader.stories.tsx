import type { Meta, StoryObj } from '@storybook/react';

import ImageUploader from './ImageUploader';

const meta: Meta<typeof ImageUploader> = {
  title: 'common/ImageUploader',
  component: ImageUploader,
};

export default meta;
type Story = StoryObj<typeof ImageUploader>;

export const Default: Story = {};
