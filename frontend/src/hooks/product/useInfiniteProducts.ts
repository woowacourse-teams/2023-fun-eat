import { useState, useRef, useEffect } from 'react';

import useCategoryProducts from './useCategoryProducts';
import useIntersectionObserver from '../useIntersectionObserver';

import type { Product } from '@/types/product';

const useInfiniteProducts = (categoryId: number, sort: string) => {
  const [page, setPage] = useState(0);
  const [products, setProducts] = useState<Product[]>([]);

  const scrollRef = useRef<HTMLDivElement>(null);

  const prevCategoryId = useRef(categoryId);
  const nextPage = prevCategoryId.current !== categoryId ? 0 : page;

  useEffect(() => {
    setPage(0);
    prevCategoryId.current = categoryId;
  }, [categoryId, sort]);

  const { data: productListResponse } = useCategoryProducts(categoryId, nextPage, sort);

  const getNextPage = () => {
    setPage((page) => page + 1);
  };

  useIntersectionObserver<HTMLDivElement>(getNextPage, scrollRef, productListResponse?.page.lastPage);

  useEffect(() => {
    if (page === 0) {
      setProducts([]);
    }

    if (!productListResponse) {
      return;
    }

    setProducts((prev) => [...prev, ...productListResponse.products]);
  }, [productListResponse?.products]);

  return { products, scrollRef };
};

export default useInfiniteProducts;
