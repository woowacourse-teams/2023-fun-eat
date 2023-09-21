import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryStoreQuery } from '@/hooks/queries/product';

const category = CATEGORY_TYPE.STORE;

const CategoryStoreList = () => {
  const { data: categories } = useCategoryStoreQuery(category);

  return (
    <CategoryStoreListContainer>
      <CategoryStoreListWrapper>
        {categories.map((menu) => (
          <Link key={menu.id} as={RouterLink} to={`products/store?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </CategoryStoreListWrapper>
    </CategoryStoreListContainer>
  );
};

export default CategoryStoreList;

const CategoryStoreListContainer = styled.div`
  overflow-x: auto;
  overflow-y: hidden;

  @media screen and (min-width: 500px) {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CategoryStoreListWrapper = styled.div`
  display: flex;
  gap: 20px;
`;
