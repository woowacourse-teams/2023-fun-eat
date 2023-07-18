import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

const NavigationBar = () => {
  return (
    <NavigationBarContainer>
      <NavigationIconWrapper>
        <SvgIcon variant="search" color={theme.colors.gray3} width={22} height={22} />
        <Text size="xs" color={theme.textColors.info}>
          검색
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="list" width={22} height={22} color={theme.colors.gray3} />
        <Text size="xs" color={theme.textColors.info}>
          목록
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="home" color={theme.colors.gray3} />
        <Text size="xs" color={theme.textColors.info}>
          홈
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="recipe" color={theme.colors.gray3} />
        <Text size="xs" color={theme.textColors.info}>
          꿀조합
        </Text>
      </NavigationIconWrapper>
      <NavigationIconWrapper>
        <SvgIcon variant="profile" color={theme.colors.gray3} />
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
  justify-content: flex-end;
  gap: 8px;
  height: 50px;
  cursor: pointer;
`;
