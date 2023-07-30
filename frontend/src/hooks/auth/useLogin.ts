import { useState } from 'react';

import { authApi } from '@/apis';

type AuthProvider = 'kakao';

const useLogin = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  const handleLogin = async (authProvider: AuthProvider) => {
    try {
      const response = await authApi.get({ params: `/${authProvider}` });
      const locationUrl = response.url;

      if (locationUrl === null) {
        throw new Error('응답 받은 주소가 없습니다.');
      }

      window.location.href = locationUrl;
    } catch (error) {
      if (!(error instanceof Error)) {
        return;
      }
      setError(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return { handleLogin, isLoading, error };
};

export default useLogin;
