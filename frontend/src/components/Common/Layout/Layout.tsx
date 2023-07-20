import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import Header from '../Header/Header';
import NavigationBar from '../NavigationBar/NavigationBar';

const Layout = ({ children }: PropsWithChildren) => {
  return (
    <LayoutContainer>
      <Header />
      <MainWrapper>{children}</MainWrapper>
      <NavigationBar />
    </LayoutContainer>
  );
};

export default Layout;

const LayoutContainer = styled.div`
  max-width: 600px;
  height: 100%;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  height: calc(100% - 120px);
  padding: 40px 20px;
  overflow-y: auto;
`;
