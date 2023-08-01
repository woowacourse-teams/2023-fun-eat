import { useMutate } from './../useMutate';

import { productApi } from '@/apis';

const useReviewRegisterForm = (productId: number) => {
  return useMutate<FormData>((data) => productApi.post({ params: `/${productId}/reviews` }, data));
};

export default useReviewRegisterForm;
