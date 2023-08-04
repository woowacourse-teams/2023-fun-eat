import { Text } from '@fun-eat/design-system';
import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import styled from 'styled-components';

import { useMember } from '@/hooks/auth';
import { useMemberValueContext } from '@/hooks/context';

const ProfilePage = () => {
  const member = useMemberValueContext();
  const getMember = useMember();

  useEffect(() => {
    getMember();
  }, []);

  if (member === null) {
    return <Navigate to="/login" replace />;
  }

  return (
    <ProfilePageContainer>
      <ProfileImage src={member.profileImage} alt={member.nickname} width={60} height={60} />
      <Text as="span" size="xl">
        {member.nickname}
      </Text>
    </ProfilePageContainer>
  );
};

export default ProfilePage;

const ProfilePageContainer = styled.div`
  display: flex;
  align-items: center;
  column-gap: 20px;
`;

const ProfileImage = styled.img`
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
`;
