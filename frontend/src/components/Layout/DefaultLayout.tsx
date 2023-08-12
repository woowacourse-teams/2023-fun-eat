import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import Header from '../Common/Header/Header';
import NavigationBar from '../Common/NavigationBar/NavigationBar';

const DefaultLayout = ({ children }: PropsWithChildren) => {
  return (
    <DefaultLayoutContainer>
      <Header />
      <MainWrapper id="main">{children}</MainWrapper>
      <NavigationBar />
    </DefaultLayoutContainer>
  );
};

export default DefaultLayout;

const DefaultLayoutContainer = styled.div`
  max-width: 600px;
  height: 100%;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  height: calc(100% - 120px);
  padding: 20px;
  overflow-y: auto;
`;
