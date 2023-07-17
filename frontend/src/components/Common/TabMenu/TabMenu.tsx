import { Button, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import type { Category } from '@/types/common';

interface TabMenuProps {
  menuVariant: 'food' | 'store';
  menuList: Category[];
}

const TabMenu = ({ menuList, menuVariant }: TabMenuProps) => {
  const [selectedMenu, setSelectedMenu] = useState(0);

  const selectMenu = (menuId: number) => {
    setSelectedMenu(menuId);
  };

  return (
    <TabMenuContainer>
      {menuList.map((menu) => {
        const isSelected = menu.id === selectedMenu;
        return (
          <li key={menu.id}>
            <Button
              type="button"
              color={isSelected ? 'white' : 'gray4'}
              variant={isSelected ? 'filled' : 'outlined'}
              size="xs"
              css={isSelected && selectedTabMenuStyles[menuVariant]}
              onClick={() => selectMenu(menu.id)}
            >
              <TabMenuButtonText size="xs" weight="bold" color={theme.textColors.sub}>
                {menu.name}
              </TabMenuButtonText>
            </Button>
          </li>
        );
      })}
    </TabMenuContainer>
  );
};

export default TabMenu;

type TabMenuStyleProps = Pick<TabMenuProps, 'menuVariant'>;

const TabMenuContainer = styled.ul`
  display: flex;
  gap: 8px;
`;

const selectedTabMenuStyles: Record<TabMenuStyleProps['menuVariant'], CSSProp> = {
  food: `
    background: ${theme.colors.gray5};
    color: ${theme.textColors.white};
  `,
  store: `
    background: ${theme.colors.primary};
    color: ${theme.textColors.default};
  `,
};

const TabMenuButtonText = styled(Text)`
  color: inherit;
`;
