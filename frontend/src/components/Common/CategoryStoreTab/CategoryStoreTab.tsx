import { Button, theme } from '@fun-eat/design-system';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

import { CATEGORY_TYPE } from '@/constants';
import { useGA } from '@/hooks/common';
import { useCategoryActionContext, useCategoryValueContext } from '@/hooks/context';
import { useCategoryStoreQuery } from '@/hooks/queries/product/useCategoryQuery';
import { getTargetCategoryName } from '@/utils/category';

const category = CATEGORY_TYPE.STORE;

const CategoryStoreTab = () => {
  const { data: categories } = useCategoryStoreQuery(category);

  const { categoryIds } = useCategoryValueContext();
  const { selectCategory } = useCategoryActionContext();
  const currentCategoryId = categoryIds[category];

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const categoryIdFromURL = queryParams.get('category');

  const { gaEvent } = useGA();

  useEffect(() => {
    if (categoryIdFromURL) {
      selectCategory(category, parseInt(categoryIdFromURL));
    }
  }, [category]);

  const handleCategoryButtonClick = (menuId: number) => {
    selectCategory(category, menuId);
    gaEvent({
      category: 'button',
      action: `${getTargetCategoryName(categories, menuId)} 카테고리 버튼 클릭`,
      label: '카테고리',
    });
  };

  return (
    <CategoryMenuContainer>
      {categories.map((menu) => {
        const isSelected = menu.id === currentCategoryId;
        return (
          <li key={menu.id}>
            <CategoryButton
              type="button"
              customHeight="30px"
              color={isSelected ? 'primary' : 'gray3'}
              size="xs"
              weight="bold"
              variant={isSelected ? 'filled' : 'outlined'}
              isSelected={isSelected}
              onClick={() => handleCategoryButtonClick(menu.id)}
              aria-pressed={isSelected}
            >
              {menu.name}
            </CategoryButton>
          </li>
        );
      })}
    </CategoryMenuContainer>
  );
};

export default CategoryStoreTab;

const CategoryMenuContainer = styled.ul`
  display: flex;
  gap: 8px;
  white-space: nowrap;
  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CategoryButton = styled(Button)<{ isSelected: boolean }>`
  padding: 6px 12px;
  ${({ isSelected }) =>
    isSelected
      ? `
        background: ${theme.colors.primary};
        color: ${theme.textColors.default};
      `
      : ''}
`;
