import { Button, Link, Text, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import useRoutePage from '@/hooks/useRoutePage';

interface TitleProps {
  headingTitle: string;
  routeDestination: string;
}

const Title = ({ headingTitle, routeDestination }: TitleProps) => {
  const { routeHome } = useRoutePage();

  return (
    <TitleContainer>
      <Button type="button" variant="transparent" onClick={routeHome} aria-label="홈 화면으로 가기">
        <SvgIconWrapper>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
        </SvgIconWrapper>
      </Button>
      <TitleLink as={RouterLink} to={routeDestination}>
        <Text as="span" size="xl" weight="bold">
          {headingTitle}
        </Text>
        <DropDownIcon variant="arrow" color={theme.colors.black} width={15} height={15} />
      </TitleLink>
    </TitleContainer>
  );
};

export default Title;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  position: relative;
`;

const SvgIconWrapper = styled.span`
  position: absolute;
  top: 8px;
  left: 0;
`;

const TitleLink = styled(Link)`
  display: flex;
  align-items: center;
  gap: 20px;
`;

const DropDownIcon = styled(SvgIcon)`
  rotate: 270deg;
`;
