import { Link, Text, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { PATH } from '@/constants/path';

interface TitleProps {
  headingTitle: string;
  routeDestination: string;
}

const Title = ({ headingTitle, routeDestination }: TitleProps) => {
  return (
    <TitleContainer>
      <HomeLink as={RouterLink} to={PATH.HOME}>
        <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
      </HomeLink>
      <TitleLink as={RouterLink} to={routeDestination} replace>
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
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

const HomeLink = styled(Link)`
  position: absolute;
  top: 8px;
  left: 0;
`;

const TitleLink = styled(Link)`
  display: flex;
  gap: 20px;
  align-items: center;
`;

const DropDownIcon = styled(SvgIcon)`
  rotate: 270deg;
`;
