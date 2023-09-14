import { Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PATH } from '@/constants/path';
import { useIntersectionObserver, useScrollRestoration } from '@/hooks/common';
import { useCategoryValueContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import type { CategoryVariant, SortOption } from '@/types/common';
import displaySlice from '@/utils/displaySlice';

interface ProductListProps {
  category: CategoryVariant;
  isHomePage?: boolean;
  selectedOption?: SortOption;
}

const ProductList = ({ category, isHomePage, selectedOption }: ProductListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const productListRef = useRef<HTMLDivElement>(null);

  const { categoryIds } = useCategoryValueContext();

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductsQuery(
    categoryIds[category],
    selectedOption?.value ?? 'reviewCount,desc'
  );

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  useScrollRestoration(categoryIds[category], productListRef);

  const productList = data.pages.flatMap((page) => page.products);
  const productsToDisplay = displaySlice(isHomePage, productList);

  return (
    <ProductListContainer ref={productListRef}>
      <ProductListWrapper>
        {productsToDisplay.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${category}/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </ProductListWrapper>
      <div ref={scrollRef} aria-hidden />
    </ProductListContainer>
  );
};
export default ProductList;

const ProductListContainer = styled.div`
  height: calc(100% - 150px);
  overflow-y: auto;
`;

const ProductListWrapper = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
