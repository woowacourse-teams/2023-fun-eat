import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import PBProductItem from '../PBProductItem/PBProductItem';

import { MoreButton } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useCategoryValueContext } from '@/hooks/context';
import { useInfiniteProductsQuery } from '@/hooks/queries/product';

const PBProductList = () => {
  const { categoryIds } = useCategoryValueContext();

  const { data: pbProductListResponse } = useInfiniteProductsQuery(categoryIds.store);
  const pbProducts = pbProductListResponse.pages.flatMap((page) => page.products);

  return (
    <>
      <PBProductListContainer>
        {pbProducts.map((pbProduct) => (
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
