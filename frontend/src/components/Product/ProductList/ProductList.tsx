import { Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PRODUCT_SORT_OPTIONS } from '@/constants';
import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import useIntersectionObserver from '@/hooks/useIntersectionObserver';
import useSortOption from '@/hooks/useSortOption';
import type { CategoryVariant } from '@/types/common';

interface ProductListProps {
  category: CategoryVariant;
}

const ProductList = ({ category }: ProductListProps) => {
  const location = useLocation();
  const isRootPath = location.pathname === '/';

  const scrollRef = useRef<HTMLDivElement>(null);

  const { selectedOption } = useSortOption(PRODUCT_SORT_OPTIONS[0]);

  const { categoryIds } = useCategoryContext();

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductsQuery(categoryIds[category], selectedOption.value);
  const productList = data?.pages.flatMap((page) => page.products);
  const productsToDisplay = isRootPath ? productList?.slice(0, 2) : productList;

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  return (
    <ProductListContainer>
      {productsToDisplay?.map((product) => (
        <li key={product.id}>
          <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${category}/${product.id}`}>
            <ProductItem product={product} />
          </Link>
        </li>
      ))}
      <div ref={scrollRef} aria-hidden />
    </ProductListContainer>
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
