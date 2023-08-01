import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { useCategoryContext } from '@/hooks/context';
import { useCategory, useInfiniteProductData } from '@/hooks/product';
import useSortOption from '@/hooks/useSortOption';
import { isCategoryVariant } from '@/types/common';

const ProductListPage = () => {
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

  const location = useLocation();
  const path = location.pathname;

  const categoryVariant = path.split('/').pop() ?? '';

  if (!isCategoryVariant(categoryVariant)) {
    return <></>;
  }

  const { categoryIds } = useCategoryContext();

  const { data: menuList } = useCategory(categoryVariant);

  const { products, scrollRef } = useInfiniteProductData(categoryIds[categoryVariant], selectedOption.value);

  return (
    <>
      <section>
        <Title headingTitle={categoryVariant === 'food' ? '공통 상품' : 'PB 상품'} />
        <Spacing size={30} />
        <CategoryMenu menuList={menuList ?? []} menuVariant={categoryVariant} />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList scrollRef={scrollRef} category={categoryVariant} productList={products ?? []} />
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
