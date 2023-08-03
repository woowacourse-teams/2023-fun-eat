import { useGet } from '../useGet';

import { categoryApi } from '@/apis';
import { PRODUCT_SORT_OPTIONS } from '@/constants';
import type { CategoryProductResponse } from '@/types/response';

const useCategoryProducts = (categoryId: number, page = 0, sort: string = PRODUCT_SORT_OPTIONS[0].value) => {
  return useGet<CategoryProductResponse>(
    () => categoryApi.get({ params: `/${categoryId}/products`, queries: `?page=${page}&sort=${sort}` }),
    [categoryId, sort, page]
  );
};

export default useCategoryProducts;
