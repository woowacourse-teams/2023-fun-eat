import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { MoreButton } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useCategoryValueContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';
import displaySlice from '@/utils/displaySlice';

interface PBProductListProps {
  isHomePage?: boolean;
}

const PBProductList = ({ isHomePage }: PBProductListProps) => {
  const { categoryIds } = useCategoryValueContext();

  const { data: pbProductListResponse } = useInfiniteProductsQuery(categoryIds.store);
  const pbProducts = pbProductListResponse.pages.flatMap((page) => page.products);
  const pbProductsToDisplay = displaySlice(isHomePage, pbProducts, 10);

  return (
    <>
      <PBProductListContainer>
        {pbProductsToDisplay.map((pbProduct) => (
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
  gap: 40px;
  overflow-x: auto;
  overflow-y: hidden;

  &::-webkit-scrollbar {
    display: none;
  }
`;
