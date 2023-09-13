import { useContext } from 'react';

import { CategoryContext } from '@/contexts/CategoryContext';

const useCategoryContext = () => {
  const { categoryIds, selectCategory, currentTabScroll, saveCurrentTabScroll } = useContext(CategoryContext);

  return { categoryIds, selectCategory, currentTabScroll, saveCurrentTabScroll };
};

export default useCategoryContext;
