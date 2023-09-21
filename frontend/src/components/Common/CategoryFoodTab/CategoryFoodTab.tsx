import { Button, theme } from '@fun-eat/design-system';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import { useCategoryActionContext, useCategoryValueContext } from '@/hooks/context';
import { useCategoryFoodQuery } from '@/hooks/queries/product/useCategoryQuery';
import type { Food } from '@/types/common';

interface CategoryFoodTabProps {
  category: Food;
}

const CategoryFoodTab = ({ category }: CategoryFoodTabProps) => {
  const { data: categories } = useCategoryFoodQuery(category);

  const { categoryIds } = useCategoryValueContext();
  const { selectCategory } = useCategoryActionContext();
  const currentCategoryId = categoryIds[category];

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const categoryIdFromURL = queryParams.get('category');

  useEffect(() => {
    if (categoryIdFromURL) {
      selectCategory(category, parseInt(categoryIdFromURL));
    }
  }, [location]);

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
              category={category}
              onClick={() => selectCategory(category, menu.id)}
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

export default CategoryFoodTab;

type CategoryMenuStyleProps = Pick<CategoryFoodTabProps, 'category'>;

const CategoryMenuContainer = styled.ul`
  display: flex;
  gap: 8px;
  white-space: nowrap;
  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CategoryButton = styled(Button)<{ isSelected: boolean } & CategoryMenuStyleProps>`
  padding: 6px 12px;
  ${({ isSelected, category }) => (isSelected ? selectedCategoryMenuStyles[category] : '')}
`;

const selectedCategoryMenuStyles: Record<CategoryMenuStyleProps['category'], CSSProp> = {
  food: `
    background: ${theme.colors.gray5};
    color: ${theme.textColors.white};
  `,
};
