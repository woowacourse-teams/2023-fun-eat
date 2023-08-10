import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import useDisplaySlice from '@/hooks/useDisplaySlice';

const PBProductList = () => {
  const { categoryIds } = useCategoryContext();

  const { data: pbPRoductListResponse } = useInfiniteProductsQuery(categoryIds.store);
  const pbProductList = pbPRoductListResponse?.pages.flatMap((page) => page.products);
  const pbProductsToDisplay = useDisplaySlice(PATH.HOME, pbProductList, 10);

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
