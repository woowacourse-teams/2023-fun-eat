import { useQuery } from '@tanstack/react-query';

import { rankApi } from '@/apis';
import type { ProductRankingResponse } from '@/types/response';

const fetchProductRanking = async () => {
  const response = await rankApi.get({ params: '/products' });
  const data: ProductRankingResponse = await response.json();
  return data;
};

const useProductRankingQuery = () => {
  return useQuery({
    queryKey: ['productRanking'],
    queryFn: () => fetchProductRanking(),
  });
};

export default useProductRankingQuery;
