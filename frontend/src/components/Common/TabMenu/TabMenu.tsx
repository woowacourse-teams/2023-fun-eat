import { Button, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled, { css } from 'styled-components';

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
            <TabMenuButton
              type="button"
              color={isSelected ? 'white' : 'gray4'}
              variant={isSelected ? 'filled' : 'outlined'}
              size="xs"
              menuVariant={menuVariant}
              isSelected={isSelected}
              onClick={() => selectMenu(menu.id)}
            >
              <TabMenuButtonText size="xs" weight="bold" color={theme.textColors.gray2}>
                {menu.name}
              </TabMenuButtonText>
            </TabMenuButton>
          </li>
        );
      })}
    </TabMenuContainer>
  );
};

export default TabMenu;

type TabMenuStyleProps = Pick<TabMenuProps, 'menuVariant'> & { isSelected?: boolean };

const TabMenuContainer = styled.ul`
  display: flex;
  gap: 8px;
`;

const selectedTabMenuStyles = {
  food: css`
    background: ${({ theme }) => theme.colors.gray5};
    color: ${({ theme }) => theme.textColors.white};
  `,
  store: css`
    background: ${({ theme }) => theme.colors.primary};
    color: ${({ theme }) => theme.textColors.black};
  `,
};

const TabMenuButton = styled(Button)<TabMenuStyleProps>`
  ${({ menuVariant, isSelected }) => css`
    ${isSelected && selectedTabMenuStyles[menuVariant]}
  `}
`;

const TabMenuButtonText = styled(Text)`
  color: inherit;
`;
