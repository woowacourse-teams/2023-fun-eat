import { useMutation, useQueryClient } from '@tanstack/react-query';

import { productApi } from '@/apis';
import type { ReviewRequest } from '@/types/review';

const headers = { 'Content-Type': 'application/json' };

const useReviewRegisterFormMutation = (productId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: ReviewRequest) =>
      productApi.post({ params: `/${productId}/reviews`, credentials: true }, headers, data),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['product', productId] }),
  });
};

export default useReviewRegisterFormMutation;
