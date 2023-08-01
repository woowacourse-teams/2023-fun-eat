import type { PropsWithChildren } from 'react';
import styled from 'styled-components';

import Header from '../Common/Header/Header';

const DetailLayout = ({ children }: PropsWithChildren) => {
  return (
    <DetailLayoutContainer>
      <Header />
      <MainWrapper>{children}</MainWrapper>
    </DetailLayoutContainer>
  );
};

export default DetailLayout;

const DetailLayoutContainer = styled.div`
  max-width: 600px;
  height: 100%;
  margin: 0 auto;
`;

const MainWrapper = styled.main`
  height: calc(100% - 60px);
  padding: 20px;
  overflow-y: auto;
`;
