import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SvgIcon } from '@/components/Common';
import { PBProductList, ProductList } from '@/components/Product';
import { ProductRankingList, ReviewRankingList } from '@/components/Rank';
import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useCategory, useCategoryProducts } from '@/hooks/product';
import { useReviewRanking } from '@/hooks/rank';

const HomePage = () => {
  const { categoryIds } = useCategoryContext();

  const { data: foodCategory } = useCategory('food');
  const { data: storeCategory } = useCategory('store');

  const { data: productListResponse } = useCategoryProducts(categoryIds.food);
  const { data: pbPRoductListResponse } = useCategoryProducts(categoryIds.store);

  const { data: reviewRankingResponse } = useReviewRanking();

  return (
    <>
      <section>
        <Heading as="h2" size="xl">
          공통 상품
        </Heading>
        <Spacing size={16} />
        <CategoryMenu menuList={foodCategory ?? []} menuVariant="food" />
        <Spacing size={12} />
        <ProductList category="food" productList={productListResponse?.products.slice(0, 2) ?? []} />
        <ProductListRouteLink as={RouterLink} to={`${PATH.PRODUCT_LIST}/food`}>
          전체 보기 <SvgIcon variant="arrow" width={12} height={12} />
        </ProductListRouteLink>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          편의점 특산품
        </Heading>
        <Spacing size={16} />
        <CategoryMenu menuList={storeCategory ?? []} menuVariant="store" />
        <Spacing size={16} />
        <PBProductList productList={pbPRoductListResponse?.products.slice(0, 3) ?? []} />
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
        <ReviewRankingList reviewRankings={reviewRankingResponse?.reviews ?? []} />
      </section>
    </>
  );
};

export default HomePage;

const ProductListRouteLink = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 12px 0;
  text-align: center;
  border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};

  svg {
    margin-left: 4px;
    transform: rotate(180deg);
  }
`;
