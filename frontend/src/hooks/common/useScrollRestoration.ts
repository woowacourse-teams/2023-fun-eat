import { useEffect } from 'react';

import useTimeout from './useTimeout';
import { useCategoryContext } from '../context';

const useScrollRestoration = (currentCategoryId: number) => {
  const { saveCurrentTabScroll } = useCategoryContext();

  const handleScroll = () => {
    const mainElement = document.getElementById('main');
    if (!mainElement) return;

    saveCurrentTabScroll(currentCategoryId, mainElement.scrollTop);
  };

  const [timeoutFn] = useTimeout(handleScroll, 300);

  useEffect(() => {
    const mainElement = document.getElementById('main');
    if (!mainElement) return;

    mainElement.addEventListener('scroll', timeoutFn);

    return () => {
      mainElement.removeEventListener('scroll', timeoutFn);
    };
  }, [currentCategoryId]);
};

export default useScrollRestoration;
