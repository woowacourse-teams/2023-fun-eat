import { BottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect, useRef, useState } from 'react';

import SortOptionList from './SortOptionList';

import { PRODUCT_SORT_OPTIONS } from '@/constants';

const meta: Meta<typeof SortOptionList> = {
  title: 'common/SortOptionList',
  component: SortOptionList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const ref = useRef<HTMLDialogElement>(null);
    const [selectedOption, setSelectedOption] = useState<string>(PRODUCT_SORT_OPTIONS[0].label);

    useEffect(() => {
      ref.current?.showModal();
    }, []);

    const closeBottomSheet = () => {
      ref.current?.close();
    };

    const selectSortOption = (selectedOptionLabel: string) => {
      setSelectedOption(selectedOptionLabel);
    };

    return (
      <BottomSheet ref={ref} close={closeBottomSheet}>
        <SortOptionList
          options={PRODUCT_SORT_OPTIONS}
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={closeBottomSheet}
        />
      </BottomSheet>
    );
  },
};
