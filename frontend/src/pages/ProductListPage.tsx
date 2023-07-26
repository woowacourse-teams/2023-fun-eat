import { BottomSheet, Spacing } from '@fun-eat/design-system';
import { useContext } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { CategoryContext } from '@/contexts/CategoryContext';
import { useCategory, useCategoryProducts } from '@/hooks/product';
import useBottomSheet from '@/hooks/useBottomSheet';
import useSortOption from '@/hooks/useSortOption';
import { isCategoryVariant } from '@/types/common';

const ProductListPage = () => {
  const { ref, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0].label);

  const location = useLocation();
  const path = location.pathname;

  const category = path.split('/').pop() ?? '';

  if (!isCategoryVariant(category)) {
    return;
  }

  const { categories } = useContext(CategoryContext);

  const { data: menuList } = useCategory(category);
  const { data: productListResponse } = useCategoryProducts(categories[category]);

  return (
    <>
      <section>
        <Title headingTitle={category === 'food' ? '공통 상품' : 'PB 상품'} />
        <Spacing size={30} />
        <CategoryMenu menuList={menuList ?? []} menuVariant={category} />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList category={category} productList={productListResponse?.products ?? []} />
      </section>
      <BottomSheet ref={ref} maxWidth="600px" close={handleCloseBottomSheet}>
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
