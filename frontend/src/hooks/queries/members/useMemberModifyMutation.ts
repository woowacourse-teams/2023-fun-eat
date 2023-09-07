import { useMutation, useQueryClient } from '@tanstack/react-query';

import { memberApi } from '@/apis';
import type { MemberRequest } from '@/types/member';

const headers = { 'Content-Type': 'application/json' };

const putModifyMember = (body: MemberRequest) => {
  return memberApi.put({ credentials: true }, headers, body);
};

const useMemberModifyMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (body: MemberRequest) => putModifyMember(body),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['member'] }),
  });
};

export default useMemberModifyMutation;
