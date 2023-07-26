import { BottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect, useRef, useState } from 'react';

import SortOptionList from './SortOptionList';

import { PRODUCT_SORT_OPTIONS } from '@/constants';
import useSortOption from '@/hooks/useSortOption';

const meta: Meta<typeof SortOptionList> = {
  title: 'common/SortOptionList',
  component: SortOptionList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const ref = useRef<HTMLDialogElement>(null);
    const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

    useEffect(() => {
      ref.current?.showModal();
    }, []);

    const closeBottomSheet = () => {
      ref.current?.close();
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
