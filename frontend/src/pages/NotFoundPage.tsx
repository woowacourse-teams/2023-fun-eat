import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { DefaultLayout } from '@/components/Layout';

const NotFoundPage = () => {
  return (
    <DefaultLayout>
      <NotFoundSection>
        <Heading size="xl">존재하지 않는 페이지입니다</Heading>
        <Spacing size={20} />
        <HomePageLink as={RouterLink} to="/">
          홈으로 가기
        </HomePageLink>
      </NotFoundSection>
    </DefaultLayout>
  );
};

export default NotFoundPage;

const NotFoundSection = styled.section`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  padding: 0 20px;
  text-align: center;
  transform: translate(-50%, -50%);
`;

const HomePageLink = styled(Link)`
  display: block;
  width: 160px;
  height: 40px;
  margin: 0 auto;
  line-height: 40px;
  border-radius: 8px;
  background-color: ${({ theme }) => theme.colors.primary};
`;
