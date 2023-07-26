import { Button, Text, theme } from '@fun-eat/design-system';
import { useContext } from 'react';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import { CategoryContext } from '@/contexts/CategoryContext';
import type { Category } from '@/types/common';

interface CategoryMenuProps {
  menuVariant: 'food' | 'store';
  menuList: Category[];
}

const CategoryMenu = ({ menuList, menuVariant }: CategoryMenuProps) => {
  const { categories, selectCategory } = useContext(CategoryContext);
  const menuId = categories[menuVariant];

  return (
    <CategoryMenuContainer>
      {menuList.map((menu) => {
        const isSelected = menu.id === menuId;
        return (
          <li key={menu.id}>
            <Button
              type="button"
              color={isSelected ? 'primary' : 'gray3'}
              variant={isSelected ? 'filled' : 'outlined'}
              size="xs"
              css={isSelected ? selectedCategoryMenuStyles[menuVariant] : undefined}
              onClick={() => selectCategory(menuVariant, menu.id)}
            >
              <Text size="xs" weight="bold">
                {menu.name}
              </Text>
            </Button>
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
