import { Button, Text } from '@fun-eat/design-system';
import { useState } from 'react';
import { css, styled } from 'styled-components';

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
      {menuList.map((menu, index) => (
        <TabMenuButton
          key={menu}
          variant={index === selectedMenu ? 'filled' : 'outlined'}
          size="xs"
          color={index === selectedMenu ? 'white' : 'gray3'}
          menuVariant={menuVariant}
          isSelected={index === selectedMenu}
          onClick={() => selectMenu(index)}
        >
          <Text size="xs" weight="bold" color={index === selectedMenu ? 'white' : 'black'}>
            {menu}
          </Text>
        </TabMenuButton>
      ))}
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
    color: ${({ theme }) => theme.colors.white};
  `,
  store: css`
    background: #ad44ff;
    color: ${({ theme }) => theme.colors.white};
  `,
};

const TabMenuButton = styled(Button)<TabMenuStyleProps>`
  ${({ menuVariant, isSelected }) => css`
    ${isSelected && menuVariant && selectedTabMenuStyles[menuVariant]}
  `}
`;
