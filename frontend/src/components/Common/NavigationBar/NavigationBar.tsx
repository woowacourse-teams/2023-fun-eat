import { Link, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { NAVIGATION_MENU } from '@/constants';

const NavigationBar = () => {
  const [selectedMenu, setSelectedMenu] = useState('홈');

  const selectMenu = (name: string) => {
    setSelectedMenu(name);
  };

  return (
    <NavigationBarContainer>
      <NavigationBarList>
        {NAVIGATION_MENU.map(({ variant, name, path }) => {
          const isSelected = selectedMenu === name;
          return (
            <NavigationItem key={variant} onClick={() => selectMenu(name)}>
              <NavigationLink as={RouterLink} to={path}>
                <SvgIcon variant={variant} color={isSelected ? theme.colors.gray5 : theme.colors.gray3} />
                <Text size="xs" color={isSelected ? theme.colors.gray5 : theme.colors.gray3}>
                  {name}
                </Text>
              </NavigationLink>
            </NavigationItem>
          );
        })}
      </NavigationBarList>
    </NavigationBarContainer>
  );
};

export default NavigationBar;

const NavigationBarContainer = styled.nav`
  width: 100%;
  height: 60px;
`;

const NavigationBarList = styled.ul`
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding-top: 12px;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-bottom: none;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
`;

const NavigationItem = styled.li`
  height: 50px;
`;

const NavigationLink = styled(Link)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  gap: 4px;
`;
