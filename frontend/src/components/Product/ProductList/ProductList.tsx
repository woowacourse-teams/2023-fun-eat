import { Button } from '@fun-eat/design-system';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import ProductItem from '../ProductItem/ProductItem';

import { PATH } from '@/constants/path';
import mockProducts from '@/mocks/data/products.json';

const ProductList = () => {
  const { products } = mockProducts;
  const navigate = useNavigate();

  const navigateToDetailPage = (productId: number) => {
    navigate(`${PATH.PRODUCT_LIST}/${productId}`);
  };

  return (
    <ProductListContainer>
      {products.map((product) => (
        <li key={product.id}>
          <ProductButton type="button" variant="filled" color="white" onClick={() => navigateToDetailPage(product.id)}>
            <ProductItem product={product} />
          </ProductButton>
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
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;

const ProductButton = styled(Button)`
  padding: 0;
  width: 100%;
  height: 100%;
`;
