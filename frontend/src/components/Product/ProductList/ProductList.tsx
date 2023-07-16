import styled from 'styled-components';

import { MOCK_PRODUCTS } from '../mock';
import ProductItem from '../ProductItem/ProductItem';

const ProductList = () => {
  return (
    <ProductListContainer>
      {MOCK_PRODUCTS.map((product) => (
        <li key={product.id}>
          <ProductItem product={product} />
        </li>
      ))}
    </ProductListContainer>
  );
};

export default ProductList;

const ProductListContainer = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.default};
  }
`;
