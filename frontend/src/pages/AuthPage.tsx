import { useEffect } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

import { useAuth } from '@/hooks/auth';

const AuthPage = () => {
  const navigate = useNavigate();

  const { provider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  const { request } = useAuth(provider ?? 'kakao', code ?? 'code');

  useEffect(() => {
    if (!code) {
      throw new Error('code가 없습니다.');
    }

    const redirect = async () => {
      const response = await request();

      if (!response) {
        throw new Error('로그인에 실패했습니다.');
      }

      const location = await response.headers.get('Location');

      if (!location) {
        throw new Error('로그인에 실패했습니다.');
      }

      navigate(location);
    };

    redirect();
  }, []);

  return <></>;
};

export default AuthPage;
