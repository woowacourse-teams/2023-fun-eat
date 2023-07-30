import styled from 'styled-components';

import Logo from '@/assets/logo.svg';

const Header = () => {
  return (
    <HeaderContainer>
      <Logo width={200} />
    </HeaderContainer>
  );
};

export default Header;

const HeaderContainer = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 60px;
`;
