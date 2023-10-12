import { useEffect, useState } from 'react';
import { Navigate, useNavigate, useParams, useSearchParams } from 'react-router-dom';

import { loginApi } from '@/apis';
import { PRODUCT_PATH_LOCAL_STORAGE_KEY } from '@/constants';
import { PATH } from '@/constants/path';
import { useLocalStorage } from '@/hooks/common';
import { useMemberQuery } from '@/hooks/queries/members';

export const AuthPage = () => {
  const { authProvider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  const { data: member, refetch: refetchMember } = useMemberQuery();
  const [location, setLocation] = useState('');
  const navigate = useNavigate();

  const { getLocalStorage, removeLocalStorage } = useLocalStorage(PRODUCT_PATH_LOCAL_STORAGE_KEY);

  const getSessionId = async () => {
    const response = await loginApi.get({
      params: `/oauth2/code/${authProvider}`,
      queries: `?code=${code}`,
      credentials: true,
    });

    if (!response) {
      throw new Error('로그인에 실패했습니다.');
    }

    const location = response.headers.get('Location');

    if (location === null) {
      throw new Error('Location이 없습니다.');
    }

    setLocation(location);
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

    const productPath = getLocalStorage();
    const redirectLocation = productPath ? productPath : location;

    navigate(redirectLocation, { replace: true });
    removeLocalStorage();
    refetchMember();
  }, [location]);

  if (member) {
    return <Navigate to={PATH.HOME} replace />;
  }

  return <></>;
};
