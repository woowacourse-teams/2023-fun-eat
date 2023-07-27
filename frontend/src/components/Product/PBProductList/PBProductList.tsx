import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { PATH } from '@/constants/path';
import type { Product } from '@/types/product';

interface PBProductListProps {
  productList: Product[];
}

const PBProductList = ({ productList }: PBProductListProps) => {
  return (
    <PBProductListContainer>
      {productList.map((pbProduct) => (
        <li key={pbProduct.id}>
          <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/store/${pbProduct.id}`}>
            <PBProductItem pbProduct={pbProduct} />
          </Link>
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
