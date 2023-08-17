import type { PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/constants/path';
import { useMemberQuery } from '@/hooks/queries/members';

const AuthLayout = ({ children }: PropsWithChildren) => {
  const { data: member } = useMemberQuery();

  if (!member) {
    return <Navigate to={PATH.LOGIN} replace />;
  }

  return children;
};

export default AuthLayout;
