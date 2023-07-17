import { BottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect, useRef, useState } from 'react';

import BottomSheetContent from './BottomSheetContent';

const meta: Meta<typeof BottomSheetContent> = {
  title: 'BottomSheetContent',
  component: BottomSheetContent,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const ref = useRef<HTMLDialogElement>(null);
    const [selectedOption, setSelectedOption] = useState(0);

    useEffect(() => {
      ref.current?.showModal();
    }, []);

    const closeBottomSheet = () => {
      ref.current?.close();
    };

    const handleSortOption = (optionIndex: number) => {
      setSelectedOption(optionIndex);
    };

    return (
      <BottomSheet ref={ref} close={closeBottomSheet}>
        <BottomSheetContent
          selectedOption={selectedOption}
          onOptionSelected={handleSortOption}
          close={closeBottomSheet}
        />
      </BottomSheet>
    );
  },
};
