import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

const NavigationBar = () => {
  return (
    <NavigationBarContainer>
      <NavigationIconWrapper>
        <SvgIcon variant="search" />
        <Text size="xs" color={theme.textColors.info}>
          검색
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="list" />
        <Text size="xs" color={theme.textColors.info}>
          목록
        </Text>
      </NavigationIconWrapper>
      <div>로고</div>
      <NavigationIconWrapper>
        <SvgIcon variant="recipe" />
        <Text size="xs" color={theme.textColors.info}>
          꿀조합
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="profile" />
        <Text size="xs" color={theme.textColors.info}>
          마이
        </Text>
      </NavigationIconWrapper>
    </NavigationBarContainer>
  );
};

export default NavigationBar;

const NavigationBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  height: 62px;
`;

const NavigationIconWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  cursor: pointer;
`;
