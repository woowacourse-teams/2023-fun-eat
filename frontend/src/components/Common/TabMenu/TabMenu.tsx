import { Button, Text } from '@fun-eat/design-system';
import { useState } from 'react';
import styled, { css } from 'styled-components';

import type { Category } from '@/types/common';

interface TabMenuProps {
  menuVariant: 'food' | 'store';
  menuList: Category[];
}

const TabMenu = ({ menuList, menuVariant }: TabMenuProps) => {
  const [selectedMenu, setSelectedMenu] = useState(0);

  const selectMenu = (index: number) => {
    setSelectedMenu(index);
  };

  return (
    <TabMenuContainer>
      {menuList.map((menu) => {
        const isSelected = menu.id === selectedMenu;
        return (
          <li key={menu.id}>
            <TabMenuButton
              type="button"
              variant={isSelected ? 'filled' : 'outlined'}
              size="xs"
              color={isSelected ? 'white' : 'gray3'}
              menuVariant={menuVariant}
              isSelected={isSelected}
              onClick={() => selectMenu(menu.id)}
            >
              <Text size="xs" weight="bold" color={isSelected ? 'white' : 'black'}>
                {menu.name}
              </Text>
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
    background: #ad44ff;
    color: ${({ theme }) => theme.textColors.white};
  `,
};

const TabMenuButton = styled(Button)<TabMenuStyleProps>`
  ${({ menuVariant, isSelected }) => css`
    ${isSelected && menuVariant && selectedTabMenuStyles[menuVariant]}
  `}
`;
