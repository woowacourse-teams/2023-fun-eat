import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Loading, NavigableSectionTitle } from '@/components/Common';
import { MembersInfo, MemberReviewList, MemberRecipeList } from '@/components/Members';
import { PATH } from '@/constants/path';

const MemberPage = () => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <MemberPageContainer>
      <Suspense fallback={<Loading />}>
        <MembersInfo />
      </Suspense>
      <Spacing size={40} />
      <NavigableSectionTitle title="내가 작성한 리뷰" routeDestination={`${PATH.MEMBER}/review`} />
      <Spacing size={5} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberReviewList isMemberPage />
        </Suspense>
      </ErrorBoundary>
      <Spacing size={45} />
      <NavigableSectionTitle title="내가 작성한 꿀조합" routeDestination={`${PATH.MEMBER}/recipe`} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberRecipeList isMemberPage />
        </Suspense>
      </ErrorBoundary>
      <Spacing size={40} />
    </MemberPageContainer>
  );
};

export default MemberPage;

const MemberPageContainer = styled.div`
  padding: 20px 20px 0;
`;
