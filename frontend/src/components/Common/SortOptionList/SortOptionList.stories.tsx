import { BottomSheet, useBottomSheet } from '@fun-eat/design-system';
import type { Meta, StoryObj } from '@storybook/react';
import { useEffect } from 'react';

import SortOptionList from './SortOptionList';

import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { useSortOption } from '@/hooks/common';

const meta: Meta<typeof SortOptionList> = {
  title: 'common/SortOptionList',
  component: SortOptionList,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const { isOpen, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
    const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

    useEffect(() => {
      handleOpenBottomSheet();
    }, []);

    return (
      <BottomSheet isOpen={isOpen} isClosing={isClosing} close={handleCloseBottomSheet}>
        <SortOptionList
          options={PRODUCT_SORT_OPTIONS}
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    );
  },
};
