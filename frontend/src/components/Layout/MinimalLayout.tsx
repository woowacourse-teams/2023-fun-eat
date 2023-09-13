import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

const MinimalLayout = ({ children }: PropsWithChildren) => {
  return (
    <MinimalLayoutContainer>
      <MainWrapper>{children}</MainWrapper>
    </MinimalLayoutContainer>
  );
};

export default MinimalLayout;

const MinimalLayoutContainer = styled.div`
  height: 100%;
  max-width: 600px;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  position: relative;
  height: 100%;
  padding: 20px;
  overflow-x: hidden;
  overflow-y: auto;
`;
