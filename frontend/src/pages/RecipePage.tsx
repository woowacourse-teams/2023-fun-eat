import { BottomSheet, Heading, Link, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useRef, useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import {
  ErrorBoundary,
  ErrorComponent,
  Loading,
  RegisterButton,
  ScrollButton,
  SortButton,
  SortOptionList,
  SvgIcon,
} from '@/components/Common';
import { RecipeList, RecipeRegisterForm } from '@/components/Recipe';
import { RECIPE_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import RecipeFormProvider from '@/contexts/RecipeFormContext';
import { useSortOption } from '@/hooks/common';

const RECIPE_PAGE_TITLE = '🍯 꿀조합';
const REGISTER_RECIPE = '꿀조합 작성하기';
const REGISTER_RECIPE_AFTER_LOGIN = '로그인 후 꿀조합을 작성할 수 있어요';

const RecipePage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerRecipe' | 'sortOption'>('sortOption');
  const { selectedOption, selectSortOption } = useSortOption(RECIPE_SORT_OPTIONS[0]);
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { reset } = useQueryErrorResetBoundary();

  const recipeRef = useRef<HTMLDivElement>(null);

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
      <TitleWrapper>
        <Title>{RECIPE_PAGE_TITLE}</Title>
        <Link as={RouterLink} to={`${PATH.SEARCH}/recipes`}>
          <SvgIcon variant="search" />
        </Link>
      </TitleWrapper>
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <SortButtonWrapper>
            <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
          </SortButtonWrapper>
          <RecipeList recipeRef={recipeRef} selectedOption={selectedOption} />
        </Suspense>
      </ErrorBoundary>
      <Spacing size={80} />
      <RecipeRegisterButtonWrapper>
        <RegisterButton
          activeLabel={REGISTER_RECIPE}
          disabledLabel={REGISTER_RECIPE_AFTER_LOGIN}
          onClick={handleOpenRegisterRecipeSheet}
        />
      </RecipeRegisterButtonWrapper>
      <ScrollButton targetRef={recipeRef} isRecipePage />
      <BottomSheet ref={ref} isClosing={isClosing} maxWidth="600px" close={handleCloseBottomSheet}>
        {activeSheet === 'sortOption' ? (
          <SortOptionList
            options={RECIPE_SORT_OPTIONS}
            selectedOption={selectedOption}
            selectSortOption={selectSortOption}
            close={handleCloseBottomSheet}
          />
        ) : (
          <RecipeFormProvider>
            <RecipeRegisterForm closeRecipeDialog={handleCloseBottomSheet} />
          </RecipeFormProvider>
        )}
      </BottomSheet>
    </>
  );
};

export default RecipePage;

const TitleWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled(Heading)`
  font-size: 24px;
`;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 20px 0;
`;

const RecipeRegisterButtonWrapper = styled.div`
  position: fixed;
  left: 20px;
  bottom: 60px;
  width: calc(100% - 40px);
  height: 80px;
  max-width: 560px;
  background: ${({ theme }) => theme.backgroundColors.default};

  @media screen and (min-width: 600px) {
    left: calc(50% - 280px);
  }
`;
