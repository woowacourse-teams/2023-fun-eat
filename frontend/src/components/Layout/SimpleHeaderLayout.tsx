import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import Header from '../Common/Header/Header';
import NavigationBar from '../Common/NavigationBar/NavigationBar';

const SimpleHeaderLayout = ({ children }: PropsWithChildren) => {
  return (
    <SimpleHeaderLayoutContainer>
      <Header hasSearch={false} />
      <MainWrapper id="main">{children}</MainWrapper>
      <NavigationBar />
    </SimpleHeaderLayoutContainer>
  );
};

export default SimpleHeaderLayout;

const SimpleHeaderLayoutContainer = styled.div`
  height: 100%;
  max-width: 600px;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  position: relative;
  height: calc(100% - 120px);
  padding: 20px;
  overflow-x: hidden;
  overflow-y: auto;
`;
