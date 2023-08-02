import { useEffect, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

import { loginApi, memberApi } from '@/apis';
import { useMemberActionContext } from '@/hooks/context';
import type { Member } from '@/types/member';

const AuthPage = () => {
  const { authProvider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');
  const navigate = useNavigate();

  const { handleNewMember } = useMemberActionContext();

  const [location, setLocation] = useState('');

  const getSessionId = async () => {
    const response = await loginApi.get({ params: `/oauth2/code/${authProvider}`, queries: `?code=${code}` });

    if (!response) {
      throw new Error('로그인에 실패했습니다.');
    }

    const location = response.headers.get('Location');

    if (location === null) {
      throw new Error('Location이 없습니다.');
    }

    setLocation(location);
  };

  const getMember = async () => {
    const response = await memberApi.get({ credentials: true });
    const member: Member = await response.json();

    handleNewMember(member);
    navigate(location, { replace: true });
  };

  useEffect(() => {
    if (!code) {
      throw new Error('code가 없습니다.');
    }

    getSessionId();
  }, []);

  useEffect(() => {
    if (location === '') {
      return;
    }

    getMember();
  }, [location]);

  return <></>;
};

export default AuthPage;
