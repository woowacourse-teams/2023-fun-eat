import { BottomSheet, useBottomSheet } from '@fun-eat/design-system';
import styled from 'styled-components';

import { ScrollButton, SortButton, SortOptionList } from '@/components/Common';
import { RecipeList } from '@/components/Recipe';
import { RECIPE_SORT_OPTIONS } from '@/constants';
import { useSortOption } from '@/hooks/common';

const RecipePage = () => {
  const { selectedOption, selectSortOption } = useSortOption(RECIPE_SORT_OPTIONS[0]);
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();

  return (
    <>
      <SortButtonWrapper>
        <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
      </SortButtonWrapper>
      <RecipeList />
      <ScrollButton />
      <BottomSheet ref={ref} isClosing={isClosing} maxWidth="600px" close={handleCloseBottomSheet}>
        <SortOptionList
          options={RECIPE_SORT_OPTIONS}
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    </>
  );
};

export default RecipePage;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 20px 0;
`;
