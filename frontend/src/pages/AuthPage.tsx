import { useEffect, useRef } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

import { memberApi } from '@/apis';
import { useAuth } from '@/hooks/auth';
import useMemberActionContext from '@/hooks/context/useMemberActionContext';

const AuthPage = () => {
  const { provider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');
  const navigate = useNavigate();

  const { handleMember } = useMemberActionContext();
  const { request } = useAuth(provider ?? 'kakao', code ?? 'code');

  const locationRef = useRef<string>('');

  useEffect(() => {
    if (!code) {
      throw new Error('code가 없습니다.');
    }

    const getSessionId = async () => {
      const response = await request();

      if (!response) {
        throw new Error('로그인에 실패했습니다.');
      }

      const location = response.headers.get('Location');
      locationRef.current = location ?? '';
    };

    getSessionId();
  }, []);

  useEffect(() => {
    if (locationRef.current === '') {
      return;
    }

    const getMember = async () => {
      const response = await memberApi.get({ credentials: true });
      const member = await response.json();

      handleMember(member);
      navigate(locationRef.current);
    };

    getMember();
  }, [locationRef.current]);

  return <></>;
};

export default AuthPage;
