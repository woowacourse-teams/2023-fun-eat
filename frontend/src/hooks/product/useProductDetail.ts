import { useGet } from '../useGet';

import { productApi } from '@/apis';
import type { ProductDetail } from '@/types/product';

const useProductDetail = (productId: string) => {
  return useGet<ProductDetail>(() => productApi.get({ params: `/${productId}` }));
};

export default useProductDetail;
