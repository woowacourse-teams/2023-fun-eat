import { BottomSheet, Button, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import { styled } from 'styled-components';

import BottomSheetContent from '../SortOptionList/SortOptionList';
import SvgIcon from '../Svg/SvgIcon';

import { SORT_OPTIONS } from '@constants';
import useBottomSheet from '@hooks/useBottomSheet';

const SortButton = () => {
  const { ref, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const [selectedOption, setSelectedOption] = useState(0);

  const selectSortOption = (optionIndex: number) => {
    setSelectedOption(optionIndex);
  };

  return (
    <>
      <Button color="white" variant="filled" onClick={handleOpenBottomSheet}>
        <SortButtonWrapper>
          <SvgIcon variant="sort" color={theme.textColors.info} width={18} height={18} />
          <Text element="span" weight="bold" color={theme.textColors.info}>
            {SORT_OPTIONS[selectedOption].label}
          </Text>
        </SortButtonWrapper>
      </Button>
      <BottomSheet ref={ref} close={handleCloseBottomSheet}>
        <BottomSheetContent
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    </>
  );
};

export default SortButton;

const SortButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 3px;
`;
