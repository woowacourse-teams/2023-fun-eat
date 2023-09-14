import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { MENU_IMAGES, MENU_NAME, STORE_IMAGES, STORE_NAMES } from '@/constants';

const MENU_LENGTH = 5;
const STORE_LENGTH = 4;

const CategoryList = () => {
  const menuList = Array.from({ length: MENU_LENGTH }, (_, index) => ({
    name: MENU_NAME[index],
    image: MENU_IMAGES[index],
  }));

  const storeList = Array.from({ length: STORE_LENGTH }, (_, index) => ({
    name: STORE_NAMES[index],
    image: STORE_IMAGES[index],
  }));

  return (
    <CategoryListContainer>
      <MenuListWrapper>
        {menuList.map((menu, index) => (
          <CategoryItem key={index} name={menu.name} image={menu.image} />
        ))}
      </MenuListWrapper>
      <StoreListWrapper>
        {storeList.map((menu, index) => (
          <CategoryItem key={index} name={menu.name} image={menu.image} />
        ))}
      </StoreListWrapper>
    </CategoryListContainer>
  );
};

export default CategoryList;

const CategoryListContainer = styled.div`
  overflow-x: auto;
  overflow-y: hidden;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const MenuListWrapper = styled.div`
  display: flex;
  gap: 15px;
`;

const StoreListWrapper = styled.div`
  display: flex;
  gap: 15px;
`;
