import { useSuspendedInfiniteQuery } from '..';

import { categoryApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const fetchProducts = async (pageParam: number, categoryId: number, sort = 'reviewCount,desc') => {
  const res = await categoryApi.get({
    params: `/${categoryId}/products`,
    queries: `?id=${pageParam}&sort=${sort}`,
  });

  const data: CategoryProductResponse = await res.json();
  return data;
};

const useInfiniteProductsQuery = (categoryId: number, sort = 'reviewCount,desc') => {
  return useSuspendedInfiniteQuery(
    ['product', categoryId, sort],
    ({ pageParam = 0 }) => fetchProducts(pageParam, categoryId, sort),
    {
      getNextPageParam: (prevResponse: CategoryProductResponse) => {
        const lastCursor = prevResponse.products[prevResponse.products.length - 1].id;
        return prevResponse.hasNext ? lastCursor : undefined;
      },
    }
  );
};

export default useInfiniteProductsQuery;
