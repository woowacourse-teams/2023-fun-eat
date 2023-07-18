import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { NAVIGATION_MENU } from '@/constants';

const NavigationBar = () => {
  return (
    <nav>
      <NavigationBarContainer>
        {NAVIGATION_MENU.map((menu) => (
          <NavigationIconWrapper key={menu.variant}>
            <SvgIcon variant={menu.variant} color={theme.colors.gray3} width={22} height={22} />
            <Text size="xs" color={theme.textColors.info}>
              {menu.name}
            </Text>
          </NavigationIconWrapper>
        ))}
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
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-bottom: none;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
`;

const NavigationIconWrapper = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
  height: 50px;
  cursor: pointer;
`;
