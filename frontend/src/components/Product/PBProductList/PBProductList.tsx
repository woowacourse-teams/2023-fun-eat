import { Link } from '@fun-eat/design-system';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/product';

const PBProductList = () => {
  const location = useLocation();
  const isRootPath = location.pathname === '/';

  const { categoryIds } = useCategoryContext();

  const { data: pbPRoductListResponse } = useInfiniteProductsQuery(categoryIds.store);
  const pbProductList = pbPRoductListResponse?.pages.flatMap((page) => page.products);
  const pbProductsToDisplay = isRootPath ? pbProductList?.slice(0, 10) : pbProductList;

  return (
    <PBProductListContainer>
      {pbProductsToDisplay?.map((pbProduct) => (
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
