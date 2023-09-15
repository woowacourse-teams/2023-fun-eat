import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useRef } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import {
  CategoryTab,
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
import { useSortOption } from '@/hooks/common';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { category } = useParams();
  const productListRef = useRef<HTMLDivElement>(null);

  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);
  const { reset } = useQueryErrorResetBoundary();

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
        <Spacing size={30} />
        <Suspense fallback={null}>
          <CategoryTab menuVariant={category} />
        </Suspense>
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <SortButtonWrapper>
              <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
            </SortButtonWrapper>
            <ProductList productListRef={productListRef} category={category} selectedOption={selectedOption} />
          </Suspense>
        </ErrorBoundary>
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
  margin-top: 20px;
`;
