import { BottomSheet, Spacing, useBottomSheet } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SortButton, SortOptionList, Title, ScrollButton } from '@/components/Common';
import { ProductList } from '@/components/Product';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import { useSortOption } from '@/hooks/common';
import { isCategoryVariant } from '@/types/common';

const PAGE_TITLE = { food: '공통 상품', store: 'PB 상품' };

const ProductListPage = () => {
  const { ref, isClosing, handleOpenBottomSheet, handleCloseBottomSheet } = useBottomSheet();
  const { selectedOption, selectSortOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

  const { category } = useParams();

  if (!category) {
    return <></>;
  }

  if (!isCategoryVariant(category)) {
    return <></>;
  }

  return (
    <>
      <section>
        <Title
          headingTitle={PAGE_TITLE[category]}
          routeDestination={PATH.PRODUCT_LIST + '/' + (category === 'store' ? 'food' : 'store')}
        />
        <Spacing size={30} />
        <CategoryMenu menuVariant={category} />
        <SortButtonWrapper>
          <SortButton option={selectedOption} onClick={handleOpenBottomSheet} />
        </SortButtonWrapper>
        <ProductList category={category} selectedOption={selectedOption} />
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
