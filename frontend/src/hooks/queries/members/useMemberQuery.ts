import { useQuery } from '@tanstack/react-query';

import { memberApi } from '@/apis';
import type { Member } from '@/types/member';

const fetchMember = async () => {
  const response = await memberApi.get({ credentials: true });
  const data: Member = await response.json();
  return data;
};

const useMemberQuery = () => {
  return useQuery({
    queryKey: ['member'],
    queryFn: fetchMember,
    staleTime: 15 * 60 * 1000,
    refetchOnWindowFocus: false,
    useErrorBoundary: false,
    onError: (error) => {
      if (error instanceof Error) {
        alert(error.message);
      }
    },
  });
};

export default useMemberQuery;
