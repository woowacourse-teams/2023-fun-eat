import { Button, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { CATEGORY_TYPE } from '@/constants';
import { useGA } from '@/hooks/common';
import { useCategoryActionContext, useCategoryValueContext } from '@/hooks/context';
import { useCategoryFoodQuery } from '@/hooks/queries/product/useCategoryQuery';
import { getTargetCategoryName } from '@/utils/category';

const categoryType = CATEGORY_TYPE.FOOD;

const CategoryFoodTab = () => {
  const { data: categories } = useCategoryFoodQuery(categoryType);

  const { categoryIds } = useCategoryValueContext();
  const { selectCategory } = useCategoryActionContext();

  const currentCategoryId = categoryIds[categoryType];

  const { gaEvent } = useGA();

  const handleCategoryButtonClick = (menuId: number) => {
    selectCategory(categoryType, menuId);
    gaEvent({
      category: 'button',
      action: `${getTargetCategoryName(categories, menuId)} 카테고리 버튼 클릭`,
      label: '카테고리',
    });
  };

  return (
    <CategoryMenuContainer>
      {categories.map(({ id, name }) => {
        const isSelected = id === currentCategoryId;
        return (
          <li key={id}>
            <CategoryButton
              type="button"
              customHeight="30px"
              color={isSelected ? 'primary' : 'gray3'}
              size="xs"
              weight="bold"
              variant={isSelected ? 'filled' : 'outlined'}
              isSelected={isSelected}
              onClick={() => handleCategoryButtonClick(id)}
              aria-pressed={isSelected}
            >
              {name}
            </CategoryButton>
          </li>
        );
      })}
    </CategoryMenuContainer>
  );
};

export default CategoryFoodTab;

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
    isSelected &&
    `
      background: ${theme.colors.gray5};
      color: ${theme.textColors.white};
      `}
`;
