import { useGet } from '../useGet';

import { rankApi } from '@/apis';
import type { ProductRanking } from '@/types/ranking';

interface ProductRankingResponse {
  products: ProductRanking[];
}

const useProductRanking = () => {
  return useGet<ProductRankingResponse>(() => rankApi.get({ params: '/products' }));
};

export default useProductRanking;
