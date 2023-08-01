import { useMutate } from '../useMutate';

import { loginApi } from '@/apis';

const useAuth = (authProvider: string, code: string) => {
  return useMutate(() => loginApi.post({ params: `/oauth2/code/${authProvider}`, queries: `?code=${code}` }));
};

export default useAuth;
