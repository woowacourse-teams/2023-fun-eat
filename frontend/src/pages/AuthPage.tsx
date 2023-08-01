import { useEffect } from 'react';
import { Navigate, useParams, useSearchParams } from 'react-router-dom';

import { useAuth } from '@/hooks/auth';

const AuthPage = () => {
  const { provider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  const { request } = useAuth(provider ?? 'kakao', code ?? 'code');

  useEffect(() => {
    if (!code) {
      throw new Error('code가 없습니다.');
    }

    request();
  }, []);

  return <Navigate to="/" />;
};

export default AuthPage;
