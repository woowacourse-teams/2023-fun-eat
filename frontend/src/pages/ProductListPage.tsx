import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useCategory, useCategoryProducts } from '@/hooks/product';
import useSortOption from '@/hooks/useSortOption';
import type { CategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

  const [categoryVariant, setCategoryVariant] = useState<CategoryVariant>('food');

  const { categoryIds } = useCategoryContext();

  const { data: menuList } = useCategory(categoryVariant);
  const { data: productListResponse } = useCategoryProducts(categoryIds[categoryVariant], selectedOption.value);

  const navigate = useNavigate();

  const handleClickTitle = () => {
    const newCategoryVariant = categoryVariant === 'store' ? 'food' : 'store';
    setCategoryVariant(newCategoryVariant);
    navigate(PATH.PRODUCT_LIST + '/' + newCategoryVariant);
  };

  return (
    <>
      <section>
        <Title headingTitle={PAGE_TITLE[categoryVariant]} handleClickTitle={handleClickTitle} />
        <Spacing size={30} />
        <CategoryMenu menuList={menuList ?? []} menuVariant={categoryVariant} />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList category={categoryVariant} productList={productListResponse?.products ?? []} />
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
