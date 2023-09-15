import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Loading, ScrollButton, SectionTitle } from '@/components/Common';
import { MemberReviewList } from '@/components/Members';

const MemberReviewPage = () => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <MemberReviewPageContainer>
      <SectionTitle name="내가 작성한 리뷰" />
      <Spacing size={18} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberReviewList />
        </Suspense>
      </ErrorBoundary>
      <ScrollButton />
      <Spacing size={40} />
    </MemberReviewPageContainer>
  );
};

export default MemberReviewPage;

const MemberReviewPageContainer = styled.div`
  padding: 20px 20px 0;
`;
