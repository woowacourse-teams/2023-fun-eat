import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';

import { ErrorBoundary, ErrorComponent, Loading, NavigableSectionTitle } from '@/components/Common';
import { MembersInfo, MemberReviewList } from '@/components/Members';
import { PATH } from '@/constants/path';
import { useMemberQuery } from '@/hooks/queries/members';

const MemberPage = () => {
  const { data: member } = useMemberQuery();
  const { reset } = useQueryErrorResetBoundary();

  // TODO: suspended query 도입 시 없어질 예정
  // member가 없다면 로그인 페이지로 이동하기 때문에 member는 항상 존재.
  if (!member) {
    return null;
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
