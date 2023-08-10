import { useQuery } from '@tanstack/react-query';

import { searchApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const fetchSearchedProducts = async (query: string) => {
  const response = await searchApi.get({ params: '/products', queries: `?query=${query}&page=1` });
  const data: CategoryProductResponse = await response.json();

  return data;
};

const useSearchedProductsQuery = (query: string) => {
  return useQuery({
    queryKey: ['search', 'products', query],
    queryFn: () => fetchSearchedProducts(query),
  });
};

export default useSearchedProductsQuery;
