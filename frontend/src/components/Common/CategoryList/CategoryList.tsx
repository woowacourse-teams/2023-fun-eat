import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryQuery } from '@/hooks/queries/product';

const CategoryList = () => {
  const { data: foodCategories } = useCategoryQuery(CATEGORY_TYPE['FOOD']);
  const { data: storeCategories } = useCategoryQuery(CATEGORY_TYPE['STORE']);

  return (
    <CategoryListContainer>
      <MenuListWrapper>
        {foodCategories.map((menu) => (
          <Link key={`menuItem-${menu.id}`} as={RouterLink} to={`products/food?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </MenuListWrapper>
      <StoreListWrapper>
        {storeCategories.map((menu) => (
          <Link key={`menuItem-${menu.id}`} as={RouterLink} to={`products/store?category=${menu.id}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </StoreListWrapper>
    </CategoryListContainer>
  );
};

export default CategoryList;

const CategoryListContainer = styled.div`
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

const MenuListWrapper = styled.div`
  display: flex;
  gap: 20px;
`;

const StoreListWrapper = styled.div`
  display: flex;
  gap: 20px;
`;
