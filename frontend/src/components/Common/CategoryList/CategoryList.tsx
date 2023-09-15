import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { MENU_IMAGES, MENU_NAME, STORE_IMAGES, STORE_NAMES } from '@/constants';

const MENU_LENGTH = 5;
const STORE_LENGTH = 4;

const menuList = Array.from({ length: MENU_LENGTH }, (_, index) => ({
  name: MENU_NAME[index],
  image: MENU_IMAGES[index],
}));

const storeList = Array.from({ length: STORE_LENGTH }, (_, index) => ({
  name: STORE_NAMES[index],
  image: STORE_IMAGES[index],
}));

const CategoryList = () => {
  return (
    <CategoryListContainer>
      <MenuListWrapper>
        {menuList.map((menu, index) => (
          <Link key={`menuItem-${index}`} as={RouterLink} to={`products/food?category=${index + 1}`}>
            <CategoryItem name={menu.name} image={menu.image} />
          </Link>
        ))}
      </MenuListWrapper>
      <StoreListWrapper>
        {storeList.map((menu, index) => (
          <Link key={`menuItem-${index}`} as={RouterLink} to={`products/store?category=${index + 6}`}>
            <CategoryItem key={`storeItem-${index}`} name={menu.name} image={menu.image} />
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
