import { BottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect, useRef } from 'react';

import BottomSheetContent from './BottomSheetContent';

const meta: Meta<typeof BottomSheetContent> = {
  title: 'common/BottomSheetContent',
  component: BottomSheetContent,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const ref = useRef<HTMLDialogElement>(null);

    useEffect(() => {
      ref.current?.showModal();
    }, []);

    const closeBottomSheet = () => {
      ref.current?.close();
    };

    return (
      <BottomSheet ref={ref} close={closeBottomSheet}>
        <BottomSheetContent close={closeBottomSheet} />
      </BottomSheet>
    );
  },
};
