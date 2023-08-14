import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';

import { ErrorBoundary, ErrorComponent, Loading, NavigableSectionTitle } from '@/components/Common';
import { MembersInfo, MemberReviewList } from '@/components/Members';
import { PATH } from '@/constants/path';

const MemberPage = () => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <>
      <Suspense fallback={null}>
        <MembersInfo />
      </Suspense>
      <Spacing size={40} />
      <NavigableSectionTitle title="내가 작성한 리뷰" routeDestination={`${PATH.MEMBER}/review`} />
      <Spacing size={24} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberReviewList isMemberPage />
        </Suspense>
      </ErrorBoundary>
    </>
  );
};

export default MemberPage;
