import { BottomSheet, Button, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';

import BottomSheetContent from '../SortOptionList/SortOptionList';

import { SORT_OPTIONS } from '@constants';
import useBottomSheet from '@hooks/useBottomSheet';

const SortButton = () => {
  const { ref, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const [selectedOption, setSelectedOption] = useState(0);

  const handleSortOption = (optionIndex: number) => {
    setSelectedOption(optionIndex);
  };

  return (
    <>
      <Button
        color="white"
        textColor={theme.textColors.info}
        size="sm"
        variant="filled"
        onClick={handleOpenBottomSheet}
      >
        <Text weight="bold">{SORT_OPTIONS[selectedOption].label}</Text>
      </Button>
      <BottomSheet ref={ref} close={handleCloseBottomSheet}>
        <BottomSheetContent
          selectedOption={selectedOption}
          onOptionSelected={handleSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    </>
  );
};

export default SortButton;
