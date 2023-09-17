import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useRef } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Loading, ScrollButton, SectionTitle } from '@/components/Common';
import { MemberReviewList } from '@/components/Members';

const MemberReviewPage = () => {
  const { reset } = useQueryErrorResetBoundary();
  const memberReviewRef = useRef<HTMLDivElement>(null);

  return (
    <MemberReviewPageContainer ref={memberReviewRef}>
      <SectionTitle name="내가 작성한 리뷰" />
      <Spacing size={18} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberReviewList />
        </Suspense>
      </ErrorBoundary>
      <ScrollButton targetRef={memberReviewRef} />
      <Spacing size={40} />
    </MemberReviewPageContainer>
  );
};

export default MemberReviewPage;

const MemberReviewPageContainer = styled.div`
  height: 100%;
  padding: 20px 20px 0;
  overflow-y: auto;
`;
