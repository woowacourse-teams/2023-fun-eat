import { BottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect, useRef, useState } from 'react';

import SortOptionList from './SortOptionList';

const meta: Meta<typeof SortOptionList> = {
  title: 'common/SortOptionList',
  component: SortOptionList,
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

    const selectSortOption = (optionIndex: number) => {
      setSelectedOption(optionIndex);
    };

    return (
      <BottomSheet ref={ref} close={closeBottomSheet}>
        <SortOptionList selectedOption={selectedOption} selectSortOption={selectSortOption} close={closeBottomSheet} />
      </BottomSheet>
    );
  },
};
