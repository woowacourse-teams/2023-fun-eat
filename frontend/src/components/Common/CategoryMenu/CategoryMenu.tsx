import { Button, theme } from '@fun-eat/design-system';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import { useCategoryContext } from '@/hooks/context';
import type { Category, CategoryVariant } from '@/types/common';

interface CategoryMenuProps {
  menuVariant: CategoryVariant;
  menuList: Category[];
}

const CategoryMenu = ({ menuList, menuVariant }: CategoryMenuProps) => {
  const { categoryIds, selectCategory } = useCategoryContext();
  const currentCategoryId = categoryIds[menuVariant];

  return (
    <CategoryMenuContainer>
      {menuList.map((menu) => {
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
              menuVariant={menuVariant}
              onClick={() => selectCategory(menuVariant, menu.id)}
            >
              {menu.name}
            </CategoryButton>
          </li>
        );
      })}
    </CategoryMenuContainer>
  );
};

export default CategoryMenu;

type CategoryMenuStyleProps = Pick<CategoryMenuProps, 'menuVariant'>;

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
  ${({ isSelected, menuVariant }) => (isSelected ? selectedCategoryMenuStyles[menuVariant] : '')}
`;

const selectedCategoryMenuStyles: Record<CategoryMenuStyleProps['menuVariant'], CSSProp> = {
  food: `
    background: ${theme.colors.gray5};
    color: ${theme.textColors.white};
  `,
  store: `
    background: ${theme.colors.primary};
    color: ${theme.textColors.default};
  `,
};
