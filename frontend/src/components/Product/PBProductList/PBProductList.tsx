import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { MoreButton } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useCategoryContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import displaySlice from '@/utils/displaySlice';

interface PBProductListProps {
  isHome?: boolean;
}

const PBProductList = ({ isHome }: PBProductListProps) => {
  const { categoryIds } = useCategoryContext();

  const { data: pbProductListResponse } = useInfiniteProductsQuery(categoryIds.store);
  const pbProductList = pbProductListResponse?.pages.flatMap((page) => page.products);
  const pbProductsToDisplay = displaySlice(isHome, pbProductList, 10);

  return (
    <>
      <PBProductListContainer>
        {pbProductsToDisplay?.map((pbProduct) => (
          <li key={pbProduct.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/store/${pbProduct.id}`}>
              <PBProductItem pbProduct={pbProduct} />
            </Link>
          </li>
        ))}
        <li>
          <MoreButton />
        </li>
      </PBProductListContainer>
    </>
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
