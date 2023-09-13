import { Button, theme } from '@fun-eat/design-system';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import { useCategoryContext } from '@/hooks/context';
import { useCategoryQuery } from '@/hooks/queries/product';
import type { CategoryVariant } from '@/types/common';

interface CategoryTabProps {
  tabVariant: CategoryVariant;
}

const CategoryTab = ({ tabVariant }: CategoryTabProps) => {
  const { data: categories } = useCategoryQuery(tabVariant);
  const { categoryIds, selectCategory } = useCategoryContext();

  const currentCategoryId = categoryIds[tabVariant];

  return (
    <CategoryTabContainer>
      {categories.map((tab) => {
        const isSelected = tab.id === currentCategoryId;
        return (
          <li key={tab.id}>
            <CategoryButton
              type="button"
              customHeight="30px"
              color={isSelected ? 'primary' : 'gray3'}
              size="xs"
              weight="bold"
              variant={isSelected ? 'filled' : 'outlined'}
              isSelected={isSelected}
              tabVariant={tabVariant}
              onClick={() => selectCategory(tabVariant, tab.id)}
              aria-pressed={isSelected}
            >
              {tab.name}
            </CategoryButton>
          </li>
        );
      })}
    </CategoryTabContainer>
  );
};

export default CategoryTab;

type CategoryTabStyleProps = Pick<CategoryTabProps, 'tabVariant'>;

const CategoryTabContainer = styled.ul`
  display: flex;
  gap: 8px;
  white-space: nowrap;
  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const CategoryButton = styled(Button)<{ isSelected: boolean } & CategoryTabStyleProps>`
  padding: 6px 12px;
  ${({ isSelected, tabVariant }) => (isSelected ? selectedCategoryTabStyles[tabVariant] : '')}
`;

const selectedCategoryTabStyles: Record<CategoryTabStyleProps['tabVariant'], CSSProp> = {
  food: `
    background: ${theme.colors.gray5};
    color: ${theme.textColors.white};
  `,
  store: `
    background: ${theme.colors.primary};
    color: ${theme.textColors.default};
  `,
};
