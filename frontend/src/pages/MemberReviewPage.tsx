import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';

import { ErrorBoundary, ErrorComponent, Loading, ScrollButton, SectionTitle } from '@/components/Common';
import { MemberReviewList } from '@/components/Members';

const MemberReviewPage = () => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <>
      <SectionTitle name="내가 작성한 리뷰" />
      <Spacing size={18} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberReviewList />
        </Suspense>
      </ErrorBoundary>
      <ScrollButton />
    </>
  );
};

export default MemberReviewPage;
