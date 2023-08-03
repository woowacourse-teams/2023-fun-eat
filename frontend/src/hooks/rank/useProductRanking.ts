import { useGet } from '../useGet';

import { rankApi } from '@/apis';
import type { ProductRankingResponse } from '@/types/response';

const useProductRanking = () => {
  return useGet<ProductRankingResponse>(() => rankApi.get({ params: '/products' }));
};

export default useProductRanking;
