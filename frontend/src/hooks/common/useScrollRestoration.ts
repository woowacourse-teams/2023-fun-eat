import { useEffect } from 'react';

import { useCategoryContext } from '../context';

const useScrollRestoration = (currentCategoryId: number) => {
  const { saveCurrentTabScroll } = useCategoryContext();

  useEffect(() => {
    const mainElement = document.getElementById('main');
    if (!mainElement) return;

    const handleScroll = () => {
      saveCurrentTabScroll(currentCategoryId, mainElement?.scrollTop);
    };

    mainElement.addEventListener('scroll', handleScroll);

    return () => {
      mainElement.removeEventListener('scroll', handleScroll);
    };
  }, [currentCategoryId]);
};

export default useScrollRestoration;
