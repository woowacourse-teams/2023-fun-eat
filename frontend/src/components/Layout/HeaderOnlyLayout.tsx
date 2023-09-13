import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import Header from '../Common/Header/Header';

const HeaderOnlyLayout = ({ children }: PropsWithChildren) => {
  return (
    <HeaderOnlyLayoutContainer>
      <Header />
      <MainWrapper id="main">{children}</MainWrapper>
    </HeaderOnlyLayoutContainer>
  );
};

export default HeaderOnlyLayout;

const HeaderOnlyLayoutContainer = styled.div`
  height: 100%;
  max-width: 600px;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  position: relative;
  height: calc(100% - 60px);
  padding: 20px;
  overflow-x: hidden;
  overflow-y: auto;
`;
