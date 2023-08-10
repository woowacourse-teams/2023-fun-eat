import { useQuery } from '@tanstack/react-query';

import { loginApi } from '@/apis';

const fetchAuth = async (authProvider: string, code: string) => {
  const response = await loginApi.get({ params: `/oauth2/code/${authProvider}`, queries: `?code=${code}` });
  const data = await response.json();
  return data;
};

const useAuthQuery = (authProvider: string, code: string) => {
  return useQuery({
    queryKey: ['auth'],
    queryFn: () => fetchAuth(authProvider, code),
  });
};

export default useAuthQuery;
