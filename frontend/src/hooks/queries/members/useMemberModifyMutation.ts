import { useMutation, useQueryClient } from '@tanstack/react-query';

import { memberApi } from '@/apis';

const putModifyMember = (body: FormData) => {
  return memberApi.putData({ credentials: true }, body);
};

const useMemberModifyMutation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (body: FormData) => putModifyMember(body),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['member'] }),
  });
};

export default useMemberModifyMutation;
