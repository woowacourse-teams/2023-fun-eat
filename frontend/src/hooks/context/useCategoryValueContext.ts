import { useContext } from 'react';

import { CategoryValueContext } from '@/contexts/CategoryContext';

const useCategoryValueContext = () => {
  const categoryValue = useContext(CategoryValueContext);
  if (categoryValue === null || categoryValue === undefined) {
    throw new Error('useCategoryValueContext는 Category Provider 안에서 사용해야 합니다.');
  }

  return categoryValue;
};

export default useCategoryValueContext;
