import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import NavigationBar from '../NavigationBar/NavigationBar';

const Layout = ({ children }: PropsWithChildren) => {
  return (
    <LayoutContainer>
      {/*아래 h1은 헤더 들어갈 공간 미리 표시, 헤더로 대체 예정*/}
      <h1
        style={{
          height: '60px',
          textAlign: 'center',
          lineHeight: '60px',
          fontSize: '24px',
          borderBottom: '1px solid gray',
        }}
      >
        펀잇 - 로망오우타해황
      </h1>
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
