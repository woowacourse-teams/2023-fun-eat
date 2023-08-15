import { useMutation } from '@tanstack/react-query';

import { memberApi } from '@/apis';

const putModifyMember = (body: FormData) => {
  return memberApi.put({ credentials: true }, body);
};

const useMemberModifyMutation = () => {
  return useMutation({
    mutationFn: (body: FormData) => putModifyMember(body),
  });
};

export default useMemberModifyMutation;
