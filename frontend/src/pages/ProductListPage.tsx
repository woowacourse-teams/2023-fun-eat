import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useRef } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import {
  CategoryFoodTab,
  CategoryStoreTab,
  SortButton,
  SortOptionList,
  ScrollButton,
  Loading,
  ErrorBoundary,
  ErrorComponent,
} from '@/components/Common';
import { ProductTitle, ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import type { CategoryIds } from '@/contexts/CategoryContext';
import { useScrollRestoration, useSortOption } from '@/hooks/common';
import { useCategoryValueContext } from '@/hooks/context';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { category } = useParams();
  const productListRef = useRef<HTMLDivElement>(null);

  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);
  const { reset } = useQueryErrorResetBoundary();

  const { categoryIds } = useCategoryValueContext();

  useScrollRestoration(categoryIds[category as keyof CategoryIds], productListRef);

  if (!category || !isCategoryVariant(category)) {
    return null;
  }

  return (
    <>
      <ProductListSection>
        <ProductTitle
          content={PAGE_TITLE[category]}
          routeDestination={PATH.PRODUCT_LIST + '/' + (category === 'store' ? 'food' : 'store')}
        />
        <Spacing size={20} />
        <Suspense fallback={null}>{category === 'food' ? <CategoryFoodTab /> : <CategoryStoreTab />}</Suspense>
        <Spacing size={20} />
        <ProductListContainer ref={productListRef}>
          <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
            <Suspense fallback={<Loading />}>
              <SortButtonWrapper>
                <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
              </SortButtonWrapper>
              <ProductList category={category} selectedOption={selectedOption} />
            </Suspense>
          </ErrorBoundary>
        </ProductListContainer>
      </ProductListSection>
      <ScrollButton targetRef={productListRef} />
      <BottomSheet ref={ref} isClosing={isClosing} maxWidth="600px" close={handleCloseBottomSheet}>
        <SortOptionList
          options={PRODUCT_SORT_OPTIONS}
          selectedOption={selectedOption}
          selectSortOption={selectSortOption}
          close={handleCloseBottomSheet}
        />
      </BottomSheet>
    </>
  );
};

export default ProductListPage;

const ProductListSection = styled.section`
  height: 100%;
`;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
`;

const ProductListContainer = styled.div`
  height: calc(100% - 100px);
  overflow-y: auto;
`;
