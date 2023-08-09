import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useRef } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useCategory, useInfiniteProductsQuery } from '@/hooks/product';
import useIntersectionObserver from '@/hooks/useIntersectionObserver';
import useSortOption from '@/hooks/useSortOption';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

  const { category } = useParams();

  if (!category) {
    return <></>;
  }

  if (!isCategoryVariant(category)) {
    return <></>;
  }

  const { categoryIds } = useCategoryContext();

  const { data: menuList } = useCategory(category);

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductsQuery(categoryIds[category], selectedOption.value);
  const products = data?.pages.flatMap((page) => page.products);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  return (
    <>
      <section>
        <Title
          headingTitle={PAGE_TITLE[category]}
          routeDestination={PATH.PRODUCT_LIST + '/' + (category === 'store' ? 'food' : 'store')}
        />
        <Spacing size={30} />
        <CategoryMenu menuList={menuList ?? []} menuVariant={category} />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList ref={scrollRef} category={category} productList={products ?? []} />
      </section>
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
