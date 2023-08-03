import { useMutate } from './../useMutate';

import { productApi, testApi } from '@/apis';

const useReviewRegisterForm = (productId: number) => {
  return useMutate<FormData>((data) => testApi.postData({ params: `/${productId}/reviews`, credentials: true }, data));
};

export default useReviewRegisterForm;
