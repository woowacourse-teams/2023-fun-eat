import { Link, Text, theme } from '@fun-eat/design-system';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { NAVIGATION_MENU } from '@/constants';

const NavigationBar = () => {
  const location = useLocation();

  return (
    <NavigationBarContainer>
      <NavigationBarList>
        {NAVIGATION_MENU.map(({ variant, name, path }) => {
          const currentPath = location.pathname.split('/')[1];
          const isSelected = currentPath === path.split('/')[1];

          return (
            <NavigationItem key={variant}>
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
  justify-content: space-around;
  align-items: center;
  padding-top: 12px;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-bottom: none;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
`;

const NavigationItem = styled.li`
  height: 50px;
`;

const NavigationLink = styled(Link)`
  display: flex;
  flex-direction: column;
  gap: 4px;
  justify-content: flex-end;
  align-items: center;
`;
