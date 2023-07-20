import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import { styled } from 'styled-components';

import { CategoryMenu } from '@/components/Common';
import { PBProductList, ProductList, ProductRankingList } from '@/components/Product';
import { ReviewRankingList } from '@/components/Review';
import { FOOD_CATEGORY_MENU, STORE_CATEGORY_MENU } from '@/constants';
import { PATH } from '@/constants/path';

const HomePage = () => {
  return (
    <>
      <section>
        <Heading as="h2" size="xl">
          공통 상품
        </Heading>
        <Spacing size={16} />
        <CategoryMenu menuList={FOOD_CATEGORY_MENU} menuVariant="food" />
        <Spacing size={12} />
        <ProductList />
        <ProductListRouteButton as={RouterLink} to={PATH.PRODUCT_LIST}>
          전체 보기
        </ProductListRouteButton>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          편의점 특산품
        </Heading>
        <Spacing size={16} />
        <CategoryMenu menuList={STORE_CATEGORY_MENU} menuVariant="store" />
        <Spacing size={16} />
        <PBProductList />
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          👑 랭킹
        </Heading>
        <Spacing size={12} />
        <ProductRankingList />
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          리뷰 랭킹
        </Heading>
        <Spacing size={12} />
        <ReviewRankingList />
      </section>
    </>
  );
};

export default HomePage;

const ProductListRouteButton = styled(Link)`
  display: block;
  width: 100%;
  padding: 12px 0;
  text-align: center;
  border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
`;
