import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import {
  CategoryMenu,
  SortButton,
  SortOptionList,
  Title,
  ScrollButton,
  Loading,
  ErrorBoundary,
  ErrorComponent,
} from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import { useScrollRestoration, useSortOption } from '@/hooks/common';
import { useCategoryValueContext } from '@/hooks/context';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { category } = useParams();

  if (!category || !isCategoryVariant(category)) {
    return null;
  }

  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);
  const { reset } = useQueryErrorResetBoundary();

  const { categoryIds, currentTabScroll } = useCategoryValueContext();
  const currentCategoryId = categoryIds[category];

  const productListRef = useRef<HTMLDivElement>(null);
  useScrollRestoration(currentCategoryId, productListRef);

  useEffect(() => {
    const scrollY = currentTabScroll[currentCategoryId];
    productListRef.current?.scrollTo(0, scrollY);
  }, [currentCategoryId]);

  return (
    <>
      <ProductListSection>
        <Title
          headingTitle={PAGE_TITLE[category]}
          routeDestination={PATH.PRODUCT_LIST + '/' + (category === 'store' ? 'food' : 'store')}
        />
        <Spacing size={30} />
        <Suspense fallback={null}>
          <CategoryMenu menuVariant={category} />
        </Suspense>
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <SortButtonWrapper>
              <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
            </SortButtonWrapper>
            <ProductListWrapper ref={productListRef}>
              <ProductList category={category} selectedOption={selectedOption} />
            </ProductListWrapper>
          </Suspense>
        </ErrorBoundary>
      </ProductListSection>
      <ScrollButton />
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
  margin-top: 20px;
`;

const ProductListWrapper = styled.div`
  height: calc(100% - 150px);
  overflow-y: auto;
`;
