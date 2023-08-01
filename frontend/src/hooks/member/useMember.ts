import { useGet } from '../useGet';

import { memberApi } from '@/apis';
import type { Member } from '@/types/member';

const useMember = () => {
  return useGet<Member>(() => memberApi.get({ credentials: true }));
};

export default useMember;
