import { useGet } from '../useGet';

import { categoryApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const useCategoryProducts = (categoryId: number) => {
  return useGet<CategoryProductResponse>(
    () => categoryApi.get({ params: `/${categoryId}/products`, queries: `?page=1&sort=price,desc ` }),
    categoryId
  );
};

export default useCategoryProducts;
