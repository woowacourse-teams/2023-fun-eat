import { Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PATH } from '@/constants/path';
import { useIntersectionObserver, useScrollRestoration } from '@/hooks/common';
import { useCategoryContext } from '@/hooks/context';
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

  const { categoryIds } = useCategoryContext();

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductsQuery(
    categoryIds[category],
    selectedOption?.value ?? 'reviewCount,desc'
  );
  const productList = data.pages.flatMap((page) => page.products);
  const productsToDisplay = displaySlice(isHomePage, productList);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);
  return (
    <>
      <ProductListContainer>
        {productsToDisplay.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${category}/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </ProductListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};
export default ProductList;

const ProductListContainer = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
