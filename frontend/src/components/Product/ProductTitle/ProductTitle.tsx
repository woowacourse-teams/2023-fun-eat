import { Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../../Common/Svg/SvgIcon';

import { PATH } from '@/constants/path';

interface ProductTitleProps {
  content: string;
  routeDestination: string;
}

const ProductTitle = ({ content, routeDestination }: ProductTitleProps) => {
  return (
    <ProductTitleContainer>
      <ProductTitleLink as={RouterLink} to={routeDestination} replace>
        <HeadingTitle>{content}</HeadingTitle>
        <DropDownIcon variant="arrow" color={theme.colors.black} width={15} height={15} />
      </ProductTitleLink>
      <Link as={RouterLink} to={`${PATH.SEARCH}/products`}>
        <SvgIcon variant="search" />
      </Link>
    </ProductTitleContainer>
  );
};

export default ProductTitle;

const ProductTitleContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  height: 30px;
`;

const ProductTitleLink = styled(Link)`
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
