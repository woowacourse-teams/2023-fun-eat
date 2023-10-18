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
import { useGA, useSortOption } from '@/hooks/common';

const RECIPE_PAGE_TITLE = '🍯 꿀조합';
const REGISTER_RECIPE = '꿀조합 작성하기';
const REGISTER_RECIPE_AFTER_LOGIN = '로그인 후 꿀조합을 작성할 수 있어요';

export const RecipePage = () => {
  const [activeSheet, setActiveSheet] = useState<'registerRecipe' | 'sortOption'>('sortOption');
  const { selectedOption, selectSortOption } = useSortOption(RECIPE_SORT_OPTIONS[0]);
  const { isOpen, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { reset } = useQueryErrorResetBoundary();
  const { gaEvent } = useGA();

  const recipeRef = useRef<HTMLDivElement>(null);

  const handleOpenRegisterRecipeSheet = () => {
    setActiveSheet('registerRecipe');
    handleOpenBottomSheet();
    gaEvent({ category: 'button', action: '꿀조합 작성하기 버튼 클릭', label: '꿀조합 작성' });
  };

  const handleOpenSortOptionSheet = () => {
    setActiveSheet('sortOption');
    handleOpenBottomSheet();
    gaEvent({ category: 'button', action: '꿀조합 정렬 버튼 클릭', label: '꿀조합 정렬' });
  };

  return (
    <>
      <TitleWrapper>
        <Title>{RECIPE_PAGE_TITLE}</Title>
        <Link as={RouterLink} to={`${PATH.SEARCH}/recipes`}>
          <SvgIcon variant="search" />
        </Link>
      </TitleWrapper>
      <Spacing size={12} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <RecipeListWrapper ref={recipeRef}>
            <SortButtonWrapper>
              <SortButton option={selectedOption} onClick={handleOpenSortOptionSheet} />
            </SortButtonWrapper>
            <RecipeList selectedOption={selectedOption} />
          </RecipeListWrapper>
        </Suspense>
      </ErrorBoundary>
      <RecipeRegisterButtonWrapper>
        <RegisterButton
          activeLabel={REGISTER_RECIPE}
          disabledLabel={REGISTER_RECIPE_AFTER_LOGIN}
          onClick={handleOpenRegisterRecipeSheet}
        />
      </RecipeRegisterButtonWrapper>
      <ScrollButton targetRef={recipeRef} isRecipePage />
      <BottomSheet isOpen={isOpen} isClosing={isClosing} maxWidth="600px" close={handleCloseBottomSheet}>
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

const TitleWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30px;
`;

const Title = styled(Heading)`
  font-size: 24px;
`;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
`;

const RecipeListWrapper = styled.div`
  height: calc(100% - 130px);
  overflow-y: auto;
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
