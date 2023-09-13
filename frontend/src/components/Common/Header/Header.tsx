import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import Logo from '@/assets/logo.svg';
import { PATH } from '@/constants/path';

const Header = () => {
  return (
    <HeaderContainer>
      <Link as={RouterLink} to={PATH.HOME}>
        <Logo width={200} />
      </Link>
    </HeaderContainer>
  );
};

export default Header;

const HeaderContainer = styled.header`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 60px;
`;
