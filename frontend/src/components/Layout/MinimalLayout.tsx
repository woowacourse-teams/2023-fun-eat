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
  max-width: 600px;
  height: 100%;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  position: relative;
  height: 100%;
  padding: 20px;
  overflow-y: auto;
`;
