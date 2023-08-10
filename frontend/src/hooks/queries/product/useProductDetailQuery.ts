import { useQuery } from '@tanstack/react-query';

import { productApi } from '@/apis';
import type { ProductDetail } from '@/types/product';

const fetchProductDetail = async (productId: string) => {
  const response = await productApi.get({ params: `/${productId}` });
  const data: ProductDetail = await response.json();
  return data;
};

const useProductDetailQuery = (productId: string) => {
  return useQuery({
    queryKey: ['productDetail', productId],
    queryFn: () => fetchProductDetail(productId),
  });
};

export default useProductDetailQuery;
