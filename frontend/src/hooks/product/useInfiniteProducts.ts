import { useInfiniteQuery } from '@tanstack/react-query';
import { useState } from 'react';

import { categoryApi } from '@/apis';
import type { Product } from '@/types/product';
import type { CategoryProductResponse } from '@/types/response';

const useInfiniteProducts = (categoryId: number, sort: string) => {
  const [products, setProducts] = useState<Product[]>([]);

  const fetchProducts = async ({ pageParam = 0 }) => {
    const res = await categoryApi.get({
      params: `/${categoryId}/products`,
      queries: `?page=${pageParam}&sort=${sort}`,
    });

    const data: CategoryProductResponse = await res.json();
    return data;
  };

  const { fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: [`products-${categoryId}`],
    queryFn: fetchProducts,
    onSuccess: (data) => {
      setProducts(() => data.pages.flatMap((page) => page.products));
    },
    getNextPageParam: (prevResponse: CategoryProductResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });

  return { fetchNextPage, hasNextPage, products };
};

export default useInfiniteProducts;
