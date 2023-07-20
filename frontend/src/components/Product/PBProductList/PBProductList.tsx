import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import pbProducts from '@/mocks/data/pbProducts.json';

const PBProductList = () => {
  return (
    <PBProductListContainer>
      {pbProducts.map((pbProduct) => (
        <li key={pbProduct.id}>
          <PBProductItem pbProduct={pbProduct} />
        </li>
      ))}
    </PBProductListContainer>
  );
};

export default PBProductList;

const PBProductListContainer = styled.ul`
  display: flex;
  overflow-x: auto;
  overflow-y: hidden;
  gap: 40px;

  &::-webkit-scrollbar {
    display: none;
  }
`;
