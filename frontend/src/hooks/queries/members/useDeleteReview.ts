import { useMutation, useQueryClient } from '@tanstack/react-query';

import { memberApi } from '@/apis';

const headers = { 'Content-Type': 'application/json' };

const deleteReview = async (reviewId: number) => {
  return memberApi.delete({ params: `/reviews/${reviewId}`, credentials: true }, headers);
};

const useDeleteReview = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (reviewId: number) => deleteReview(reviewId),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['member', 'review'] }),
  });
};

export default useDeleteReview;
