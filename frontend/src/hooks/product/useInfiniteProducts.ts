import { useState, useRef, useEffect } from 'react';

import useCategoryProducts from './useCategoryProducts';
import useIntersectionObserver from '../useIntersectionObserver';

import type { Product } from '@/types/product';

const useInfiniteProducts = (categoryId: number, sort: string) => {
  const [page, setPage] = useState(0);
  const [products, setProducts] = useState<Product[]>([]);

  const scrollRef = useRef<HTMLDivElement>(null);

  const { data: productListResponse } = useCategoryProducts(categoryId, page, sort);

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

export default useInfiniteProducts;
