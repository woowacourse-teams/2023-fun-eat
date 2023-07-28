import { useContext } from 'react';

import { CategoryContext } from '@/contexts/CategoryContext';

const useCategoryContext = () => {
  const { categoryIds, selectCategory } = useContext(CategoryContext);

  return { categoryIds, selectCategory };
};

export default useCategoryContext;
