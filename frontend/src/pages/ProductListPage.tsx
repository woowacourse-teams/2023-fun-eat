import { Spacing } from '@fun-eat/design-system';
import { useContext } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu } from '@/components/Common';
import SortButton from '@/components/Common/SortButton/SortButton';
import Title from '@/components/Common/Title/Title';
import { ProductList } from '@/components/Product';
import { CategoryContext } from '@/contexts/CategoryContext';
import { useCategory, useCategoryProducts } from '@/hooks/product';
import { isCategoryVariant } from '@/types/common';

const ProductListPage = () => {
  const location = useLocation();
  const path = location.pathname;

  const category = path.split('/').pop() ?? '';

  if (!isCategoryVariant(category)) {
    return;
  }

  const { categories } = useContext(CategoryContext);

  const { data: menuList } = useCategory(category);
  const { data } = useCategoryProducts(categories[category]);

  return (
    <section>
      <Title headingTitle={category === 'food' ? '공통 상품' : 'PB 상품'} />
      <Spacing size={30} />
      <CategoryMenu menuList={menuList ?? []} menuVariant={category} />
      <SortButtonWrapper>
        <SortButton />
      </SortButtonWrapper>
      <ProductList category={category} productList={data?.products ?? []} />
    </section>
  );
};

export default ProductListPage;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
`;
