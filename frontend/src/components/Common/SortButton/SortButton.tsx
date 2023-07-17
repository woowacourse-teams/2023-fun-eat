import { BottomSheet, Button, Text, theme } from '@fun-eat/design-system';
import { useRef, useState } from 'react';

import BottomSheetContent from '../BottomSheetContent/BottomSheetContent';

import { SORT_OPTIONS } from '@constants';

const SortButton = () => {
  const ref = useRef<HTMLDialogElement>(null);
  const [selectedOption, setSelectedOption] = useState(0);

  const handleOpenBottomSheet = () => {
    ref.current?.showModal();
  };

  const handleCloseBottomSheet = () => {
    ref.current?.close();
  };

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
        <Text weight="bold">{SORT_OPTIONS[selectedOption]}</Text>
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
