import { Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import { CategoryMenu } from '@/components/Common';
import SortButton from '@/components/Common/SortButton/SortButton';
import Title from '@/components/Common/Title/Title';
import { ProductList } from '@/components/Product';
import foodCategory from '@/mocks/data/foodCategory.json';

const ProductListPage = () => {
  return (
    <section>
      <Title headingTitle="공통 상품" />
      <Spacing size={30} />
      <CategoryMenu menuList={foodCategory} menuVariant="food" />
      <SortButtonWrapper>
        <SortButton />
      </SortButtonWrapper>
      <ProductList />
    </section>
  );
};

export default ProductListPage;

const SortButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
`;
