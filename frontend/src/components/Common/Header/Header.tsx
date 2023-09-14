import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import Logo from '@/assets/logo.svg';
import { PATH } from '@/constants/path';

interface HeaderProps {
  hasSearch?: boolean;
}

const Header = ({ hasSearch = true }: HeaderProps) => {
  if (hasSearch) {
    return (
      <HeaderWithSearchContainer>
        <Link as={RouterLink} to={PATH.HOME}>
          <Logo width={160} />
        </Link>
        <Link as={RouterLink} to={`${PATH.SEARCH}/integrated`}>
          <SvgIcon variant="search" />
        </Link>
      </HeaderWithSearchContainer>
    );
  }

  return (
    <HeaderContainer>
      <Link as={RouterLink} to={PATH.HOME}>
        <Logo width={180} />
      </Link>
    </HeaderContainer>
  );
};

export default Header;

const HeaderWithSearchContainer = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: calc(100% - 40px);
  height: 60px;
  margin: 0 auto;
`;

const HeaderContainer = styled.header`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 60px;
`;
