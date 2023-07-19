import styled from 'styled-components';

import logo from '@/assets/logo.svg';

const Header = () => {
  return (
    <HeaderContainer>
      <img src={logo} width={206} alt="펀잇 로고" />
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
