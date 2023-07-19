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
            <Link as={RouterLink} to={path} key={variant}>
              <NavigationItem onClick={() => selectMenu(name)}>
                <SvgIcon variant={variant} color={isSelected ? theme.colors.gray5 : theme.colors.gray3} />
                <Text size="xs" color={isSelected ? theme.colors.gray5 : theme.colors.gray3}>
                  {name}
                </Text>
              </NavigationItem>
            </Link>
          );
        })}
      </NavigationBarList>
    </NavigationBarContainer>
  );
};

export default NavigationBar;

const NavigationBarContainer = styled.nav`
  position: absolute;
  bottom: 0;
  width: 100%;
`;

const NavigationBarList = styled.ul`
  display: flex;
  align-items: center;
  justify-content: space-around;
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
