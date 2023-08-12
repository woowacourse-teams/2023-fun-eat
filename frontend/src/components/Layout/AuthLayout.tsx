import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

const AuthLayout = ({ children }: PropsWithChildren) => {
  return (
    <AuthLayoutContainer>
      <MainWrapper>{children}</MainWrapper>
    </AuthLayoutContainer>
  );
};

export default AuthLayout;

const AuthLayoutContainer = styled.div`
  max-width: 600px;
  height: 100%;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  height: 100%;
  padding: 20px;
  overflow-y: auto;
`;
