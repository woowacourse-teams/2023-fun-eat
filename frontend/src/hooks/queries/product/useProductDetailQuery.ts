import { useSuspendedQuery } from '..';

import { productApi } from '@/apis';
import type { ProductDetail } from '@/types/product';

const fetchProductDetail = async (productId: number) => {
  const response = await productApi.get({ params: `/${productId}` });
  const data: ProductDetail = await response.json();
  return data;
};

const useProductDetailQuery = (productId: number) => {
  return useSuspendedQuery(['product', productId, 'detail'], () => fetchProductDetail(productId));
};

export default useProductDetailQuery;
