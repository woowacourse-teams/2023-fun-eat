import { useMutation, useQueryClient } from '@tanstack/react-query';

import { productApi } from '@/apis';

const useReviewRegisterFormMutation = (productId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (data: FormData) => productApi.postData({ params: `/${productId}/reviews`, credentials: true }, data),
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ['productReviews', productId, 'createdAt,desc', 'productDetail'] }),
  });
};

export default useReviewRegisterFormMutation;
