import { Button, Text } from '@fun-eat/design-system';
import { useState } from 'react';
import styled, { css } from 'styled-components';

interface TabMenuProps {
  menuVariant: 'food' | 'store';
  menuList: string[];
}

const TabMenu = ({ menuList, menuVariant }: TabMenuProps) => {
  const [selectedMenu, setSelectedMenu] = useState(0);

  const selectMenu = (index: number) => {
    setSelectedMenu(index);
  };

  return (
    <TabMenuContainer>
      {menuList.map((menu, index) => {
        const isSelected = index === selectedMenu;
        return (
          <TabMenuButton
            key={menu}
            type="button"
            variant={isSelected ? 'filled' : 'outlined'}
            size="xs"
            color={isSelected ? 'white' : 'gray3'}
            menuVariant={menuVariant}
            isSelected={isSelected}
            onClick={() => selectMenu(index)}
          >
            <Text size="xs" weight="bold" color={isSelected ? 'white' : 'black'}>
              {menu}
            </Text>
          </TabMenuButton>
        );
      })}
    </TabMenuContainer>
  );
};

export default TabMenu;

type TabMenuStyleProps = Pick<TabMenuProps, 'menuVariant'> & { isSelected?: boolean };

const TabMenuContainer = styled.div`
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
