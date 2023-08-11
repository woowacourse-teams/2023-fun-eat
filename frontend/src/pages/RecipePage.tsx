import { BottomSheet, Button, Heading, Link, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ScrollButton, SortButton, SortOptionList, SvgIcon } from '@/components/Common';
import { RecipeList } from '@/components/Recipe';
import { RECIPE_SORT_OPTIONS } from '@/constants';
import { useSortOption } from '@/hooks/common';
import { useMemberValueContext } from '@/hooks/context';

const RecipePage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerRecipe' | 'sortOption'>('sortOption');
  const { selectedOption, selectSortOption } = useSortOption(RECIPE_SORT_OPTIONS[0]);
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();

  const member = useMemberValueContext();

  const handleOpenRegisterRecipeSheet = () => {
    setActiveSheet('registerRecipe');
    handleOpenBottomSheet();
  };

  const handleOpenSortOptionSheet = () => {
    setActiveSheet('sortOption');
    handleOpenBottomSheet();
  };

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
        <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
      </SortButtonWrapper>
      <RecipeList />
      <Spacing size={80} />
      <RecipeRegisterButtonWrapper>
        <RecipeRegisterButton
          type="button"
          customWidth="100%"
          customHeight="60px"
          color={!member ? 'gray3' : 'primary'}
          textColor={!member ? 'white' : 'default'}
          size="lg"
          weight="bold"
          onClick={handleOpenRegisterRecipeSheet}
        >
          {member ? '꿀조합 작성하기' : '로그인 후 꿀조합을 작성할 수 있어요'}
        </RecipeRegisterButton>
      </RecipeRegisterButtonWrapper>
      <ScrollButton />
      <BottomSheet ref={ref} isClosing={isClosing} maxWidth="600px" close={handleCloseBottomSheet}>
        {activeSheet === 'sortOption' ? (
          <SortOptionList
            options={RECIPE_SORT_OPTIONS}
            selectedOption={selectedOption}
            selectSortOption={selectSortOption}
            close={handleCloseBottomSheet}
          />
        ) : null}
        {/*폼 추가되면 여기에 들어갈 예정*/}
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

const RecipeRegisterButtonWrapper = styled.div`
  position: fixed;
  bottom: 80px;
  left: 20px;
  width: calc(100% - 100px);
  max-width: 500px;
  height: 60px;

  @media screen and (min-width: 600px) {
    left: calc(50% - 280px);
  }
`;

const RecipeRegisterButton = styled(Button)`
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
