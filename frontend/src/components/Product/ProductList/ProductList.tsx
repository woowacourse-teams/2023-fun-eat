import { Link } from '@fun-eat/design-system';
import type { RefObject } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PATH } from '@/constants/path';
import type { CategoryVariant } from '@/types/common';
import type { Product } from '@/types/product';

interface ProductListProps {
  category: CategoryVariant;
  productList: Product[];
  scrollRef?: RefObject<HTMLDivElement>;
}

const ProductList = ({ category, productList, scrollRef }: ProductListProps) => {
  return (
    <ProductListContainer>
      {productList.map((product) => (
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
