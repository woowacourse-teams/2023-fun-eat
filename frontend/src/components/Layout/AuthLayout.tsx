import type { PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { useMemberQuery } from '@/hooks/queries/members';

const AuthLayout = ({ children }: PropsWithChildren) => {
  const { data: member } = useMemberQuery();

  if (!member) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default AuthLayout;
