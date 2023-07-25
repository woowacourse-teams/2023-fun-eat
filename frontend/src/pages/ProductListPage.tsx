import { BottomSheet, Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import useBottomSheet from '@/hooks/useBottomSheet';
import useSortOption from '@/hooks/useSortOption';
import foodCategory from '@/mocks/data/foodCategory.json';

const ProductListPage = () => {
  const { ref, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0].label);

  return (
    <>
      <section>
        <Title headingTitle="공통 상품" />
        <Spacing size={30} />
        <CategoryMenu menuList={foodCategory} menuVariant="food" />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList />
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
