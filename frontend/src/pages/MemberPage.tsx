import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useEffect } from 'react';
import { Navigate } from 'react-router-dom';

import { ErrorBoundary, ErrorComponent, Loading, NavigableSectionTitle } from '@/components/Common';
import { MembersInfo, MemberReviewList } from '@/components/Members';
import { PATH } from '@/constants/path';
import { useMember } from '@/hooks/auth';
import { useMemberValueContext } from '@/hooks/context';

const MemberPage = () => {
  const member = useMemberValueContext();
  const getMember = useMember();
  const { reset } = useQueryErrorResetBoundary();

  useEffect(() => {
    getMember();
  }, []);

  if (member === null) {
    return <Navigate to={PATH.LOGIN} replace />;
  }

  return (
    <>
      <MembersInfo member={member} />
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
