import { useMutation, useQueryClient } from '@tanstack/react-query';

import { productApi } from '@/apis';

const useReviewRegisterForm = (productId: number) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: (data: FormData) => productApi.postData({ params: `/${productId}/reviews`, credentials: true }, data),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['productReviews', productId] }),
  });

  return { mutate };
};

export default useReviewRegisterForm;
