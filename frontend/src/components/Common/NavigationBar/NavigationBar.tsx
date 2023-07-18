import { Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { NAVIGATION_MENU } from '@/constants';

const NavigationBar = () => {
  const [selectedMenu, setSelectedMenu] = useState('홈');

  const navigateMenu = (name: string) => {
    setSelectedMenu(name);
  };

  return (
    <nav>
      <NavigationBarContainer>
        {NAVIGATION_MENU.map(({ variant, name }) => {
          const isSelected = selectedMenu === name;
          return (
            <NavigationItem key={variant} onClick={() => navigateMenu(name)}>
              <SvgIcon variant={variant} color={isSelected ? theme.colors.gray5 : theme.colors.gray3} />
              <Text size="xs" color={isSelected ? theme.colors.gray5 : theme.colors.gray3}>
                {name}
              </Text>
            </NavigationItem>
          );
        })}
      </NavigationBarContainer>
    </nav>
  );
};

export default NavigationBar;

const NavigationBarContainer = styled.ul`
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  height: 62px;
  padding-top: 12px;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-bottom: none;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
`;

const NavigationItem = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
  height: 50px;
  cursor: pointer;
`;
