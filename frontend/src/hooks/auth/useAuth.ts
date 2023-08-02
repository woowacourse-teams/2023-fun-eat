import { useGet } from '../useGet';

import { loginApi } from '@/apis';

const useAuth = (authProvider: string, code: string) => {
  return useGet(() => loginApi.get({ params: `/oauth2/code/${authProvider}`, queries: `?code=${code}` }));
};

export default useAuth;
