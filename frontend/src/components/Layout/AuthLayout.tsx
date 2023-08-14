import { useEffect } from 'react';
import type { PropsWithChildren } from 'react';
import { useNavigate } from 'react-router-dom';

import { useMemberQuery } from '@/hooks/queries/members';

const AuthLayout = ({ children }: PropsWithChildren) => {
  const { data: member } = useMemberQuery();
  const navigate = useNavigate();

  useEffect(() => {
    if (member) {
      return;
    }

    alert('로그인이 필요합니다.');
    navigate('/login', { replace: true });
  }, []);

  return children;
};

export default AuthLayout;
