import { useSuspendedQuery } from '..';

import { memberApi } from '@/apis';
import type { Member } from '@/types/member';

const fetchMember = async () => {
  const response = await memberApi.get({ credentials: true });
  const data: Member = await response.json();
  return data;
};

const useMemberQuery = () => {
  return useSuspendedQuery(['member'], fetchMember, {
    staleTime: 15 * 60 * 1000,
    refetchOnWindowFocus: false,
    useErrorBoundary: false,
  });
};

export default useMemberQuery;
