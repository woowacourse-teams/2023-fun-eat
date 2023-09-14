import type { RefObject } from 'react';
import { useEffect } from 'react';

import useTimeout from './useTimeout';
import { useCategoryActionContext, useCategoryValueContext } from '../context';

const useScrollRestoration = (currentCategoryId: number, ref: RefObject<HTMLElement>) => {
  const { saveCurrentTabScroll } = useCategoryActionContext();
  const { currentTabScroll } = useCategoryValueContext();

  const handleScroll = () => {
    if (!ref.current) {
      return;
    }
    saveCurrentTabScroll(currentCategoryId, ref.current.scrollTop);
  };

  const [timeoutFn] = useTimeout(handleScroll, 300);

  useEffect(() => {
    if (!ref.current) {
      return;
    }

    ref.current.addEventListener('scroll', timeoutFn);

    const scrollY = currentTabScroll[currentCategoryId];
    ref.current.scrollTo(0, scrollY);

    return () => {
      ref.current?.removeEventListener('scroll', timeoutFn);
    };
  }, [currentCategoryId]);
};

export default useScrollRestoration;
