import { Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useCategoryValueContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import type { CategoryVariant, SortOption } from '@/types/common';

interface ProductListProps {
  category: CategoryVariant;
  selectedOption?: SortOption;
}

const ProductList = ({ category, selectedOption }: ProductListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const { categoryIds } = useCategoryValueContext();

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductsQuery(
    categoryIds[category],
    selectedOption?.value ?? 'reviewCount,desc'
  );

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const productList = data.pages.flatMap((page) => page.products);

  return (
    <>
      <ProductListContainer>
        {productList.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${category}/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </ProductListContainer>
      <div ref={scrollRef} aria-hidden style={{ height: '1px' }} />
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
