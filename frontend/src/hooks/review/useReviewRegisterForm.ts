import { useMutate } from './../useMutate';

import { productApi } from '@/apis';

const useReviewRegisterForm = (productId: number) => {
  return useMutate<FormData>((data) =>
    productApi.postData({ params: `/${productId}/reviews`, credentials: true }, data)
  );
};

export default useReviewRegisterForm;
