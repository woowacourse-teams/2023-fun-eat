import { useGet } from '../useGet';

import { categoryApi } from '@/apis';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import type { CategoryProductResponse } from '@/types/response';

const useCategoryProducts = (categoryId: number, sort: string = PRODUCT_SORT_OPTIONS[0].value) => {
  return useGet<CategoryProductResponse>(
    () => categoryApi.get({ params: `/${categoryId}/products`, queries: `?page=1&sort=${sort}` }),
    [categoryId, sort]
  );
};

export default useCategoryProducts;
