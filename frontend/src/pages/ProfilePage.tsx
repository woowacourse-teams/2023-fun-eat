import { Navigate } from 'react-router-dom';

import useMemberValueContext from '@/hooks/context/useMemberValueContext';

const ProfilePage = () => {
  const member = useMemberValueContext();

  if (member === null) {
    return <Navigate to="/login" />;
  }

  return <div>{member.nickname}</div>;
};

export default ProfilePage;
