import { Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../../Common/Svg/SvgIcon';

import { PATH } from '@/constants/path';

interface TitleProps {
  headingTitle: string;
  routeDestination: string;
}

const Title = ({ headingTitle, routeDestination }: TitleProps) => {
  return (
    <TitleContainer>
      <TitleLink as={RouterLink} to={routeDestination} replace>
        <HeadingTitle>{headingTitle}</HeadingTitle>
        <DropDownIcon variant="arrow" color={theme.colors.black} width={15} height={15} />
      </TitleLink>
      <Link as={RouterLink} to={`${PATH.SEARCH}/products`}>
        <SvgIcon variant="search" />
      </Link>
    </TitleContainer>
  );
};

export default Title;

const TitleContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const TitleLink = styled(Link)`
  display: flex;
  gap: 20px;
  align-items: center;
  margin-left: 36%;
`;

const HeadingTitle = styled(Heading)`
  font-size: 2.4rem;
`;

const DropDownIcon = styled(SvgIcon)`
  rotate: 270deg;
`;
