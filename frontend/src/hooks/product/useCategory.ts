import { useGet } from '../useGet';

import { categoryApi } from '@/apis';
import type { Category } from '@/types/common';

const useCategory = (type: string) => {
  return useGet<Category[]>(() => categoryApi.get('', `?type=${type}`));
};

export default useCategory;
