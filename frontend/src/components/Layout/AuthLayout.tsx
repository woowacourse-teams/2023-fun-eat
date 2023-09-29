import { Navigate } from 'react-router-dom';

import { PATH } from '@/constants/path';
import { useMemberQuery } from '@/hooks/queries/members';

interface AuthLayoutProps {
  children: JSX.Element;
}

const AuthLayout = ({ children }: AuthLayoutProps) => {
  const { data: member } = useMemberQuery();

  if (!member) {
    return <Navigate to={PATH.LOGIN} replace />;
  }

  return children;
};

export default AuthLayout;
