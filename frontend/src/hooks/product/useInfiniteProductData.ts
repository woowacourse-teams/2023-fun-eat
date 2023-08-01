import { useState, useRef, useEffect } from 'react';

import { useGet } from '../useGet';
import useIntersectionObserver from '../useIntersectionObserver';

import { categoryApi } from '@/apis';
import type { Product } from '@/types/product';
import type { CategoryProductResponse } from '@/types/response';

const useInfiniteProductData = (categoryId: number, option: string) => {
  const [page, setPage] = useState(0);
  const [products, setProducts] = useState<Product[]>([]);

  const scrollRef = useRef<HTMLDivElement>(null);

  const { data: productListResponse } = useGet<CategoryProductResponse>(
    () => categoryApi.get({ params: `/${categoryId}/products`, queries: `?page=${page}&sort=${option}` }),
    [page, categoryId]
  );

  const getNextPage = () => {
    setPage((page) => page + 1);
  };

  useIntersectionObserver<HTMLDivElement>(getNextPage, scrollRef, productListResponse?.page.lastPage);

  useEffect(() => {
    setPage(0);
  }, [categoryId]);

  useEffect(() => {
    if (!productListResponse) {
      return;
    }

    if (page === 0) {
      setProducts([]);
    }

    setProducts((prev) => [...prev, ...productListResponse.products]);
  }, [productListResponse?.products]);

  return { products, scrollRef };
};

export default useInfiniteProductData;
