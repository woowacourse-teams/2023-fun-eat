import { Button, theme } from '@fun-eat/design-system';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import { useScrollRestoration } from '@/hooks/common';
import { useCategoryContext } from '@/hooks/context';
import { useCategoryQuery } from '@/hooks/queries/product';
import type { CategoryVariant } from '@/types/common';

interface CategoryMenuProps {
  menuVariant: CategoryVariant;
}

const CategoryMenu = ({ menuVariant }: CategoryMenuProps) => {
  const { data: categories } = useCategoryQuery(menuVariant);
  const { categoryIds, selectCategory } = useCategoryContext();
  const currentCategoryId = categoryIds[menuVariant];

  useScrollRestoration(currentCategoryId);

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
              menuVariant={menuVariant}
              onClick={() => selectCategory(menuVariant, menu.id)}
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
