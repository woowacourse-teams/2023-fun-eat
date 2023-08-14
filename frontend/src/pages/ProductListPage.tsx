import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
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
import { useSortOption } from '@/hooks/common';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);
  const { reset } = useQueryErrorResetBoundary();

  const { category } = useParams();

  if (!category) {
    return null;
  }

  if (!isCategoryVariant(category)) {
    return null;
  }

  return (
    <>
      <section>
        <Title
          headingTitle={PAGE_TITLE[category]}
          routeDestination={PATH.PRODUCT_LIST + '/' + (category === 'store' ? 'food' : 'store')}
        />
        <Spacing size={30} />
        <Suspense fallback={null}>
          <CategoryMenu menuVariant={category} />
        </Suspense>
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ProductList category={category} selectedOption={selectedOption} />
          </Suspense>
        </ErrorBoundary>
      </section>
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

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 20px 0;
`;
