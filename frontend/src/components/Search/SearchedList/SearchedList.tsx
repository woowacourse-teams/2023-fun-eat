import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ProductItem } from '@/components/Product';
import { PATH } from '@/constants/path';
import mockProducts from '@/mocks/data/products.json';

const SearchedList = () => {
  const { products } = mockProducts;

  return (
    <>
      <SearchedListContainer>
        {products.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/food/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </SearchedListContainer>
      {/*<div ref={ref} aria-hidden />*/}
    </>
  );
};

export default SearchedList;

const SearchedListContainer = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
