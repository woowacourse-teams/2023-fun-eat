import { BottomSheet, Heading, Link, useBottomSheet } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ScrollButton, SortButton, SortOptionList, SvgIcon } from '@/components/Common';
import { RecipeList } from '@/components/Recipe';
import { RECIPE_SORT_OPTIONS } from '@/constants';
import { useSortOption } from '@/hooks/common';

const RecipePage = () => {
  const { selectedOption, selectSortOption } = useSortOption(RECIPE_SORT_OPTIONS[0]);
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();

  return (
    <>
      <Title size="xl" weight="bold">
        🍯 꿀조합
      </Title>
      <LinkWrapper>
        <Link as={RouterLink} to="/search">
          <SvgIcon variant="search" width={24} height={24} />
        </Link>
      </LinkWrapper>
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

const Title = styled(Heading)`
  font-size: 24px;
`;

const LinkWrapper = styled.div`
  position: absolute;
  top: 24px;
  right: 20px;
`;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 20px 0;
`;
