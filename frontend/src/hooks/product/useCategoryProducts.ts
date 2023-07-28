import { useGet } from '../useGet';

import { categoryApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const useCategoryProducts = (categoryId: number, sort = 'price,desc') => {
  return useGet<CategoryProductResponse>(
    () => categoryApi.get({ params: `/${categoryId}/products`, queries: `?page=1&sort=${sort}` }),
    [categoryId, sort]
  );
};

export default useCategoryProducts;
