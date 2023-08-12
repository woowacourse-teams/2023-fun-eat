import { Spacing } from '@fun-eat/design-system';
import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import styled from 'styled-components';

import { NavigableSectionTitle } from '@/components/Common';
import { MembersInfo, MemberReviewList } from '@/components/Members';
import { PATH } from '@/constants/path';
import { useMember } from '@/hooks/auth';
import { useMemberValueContext } from '@/hooks/context';

const ProfilePage = () => {
  const member = useMemberValueContext();
  const getMember = useMember();

  useEffect(() => {
    getMember();
  }, []);

  if (member === null) {
    return <Navigate to={PATH.LOGIN} replace />;
  }

  return (
    <ProfilePageContainer>
      <MembersInfo member={member} />
      <Spacing size={40} />
      <NavigableSectionTitle title="내가 작성한 리뷰" routeDestination={`${PATH.PROFILE}/review`} />
      <Spacing size={24} />
      <MemberReviewList />
    </ProfilePageContainer>
  );
};

export default ProfilePage;

const ProfilePageContainer = styled.div``;
