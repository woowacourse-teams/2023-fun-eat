import { useContext } from 'react';

import { CategoryActionContext } from '@/contexts/CategoryContext';

const useCategoryActionContext = () => {
  const categoryAction = useContext(CategoryActionContext);
  if (categoryAction === null || categoryAction === undefined) {
    throw new Error('useCategoryActionContext는 Category Provider 안에서 사용해야 합니다.');
  }

  return categoryAction;
};

export default useCategoryActionContext;
