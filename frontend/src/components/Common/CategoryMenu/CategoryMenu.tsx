import { Button, Text, theme } from '@fun-eat/design-system';
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
