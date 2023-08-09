import type { RefObject } from 'react';

const useScroll = () => {
  const scrollToTop = () => {
    const mainElement = document.getElementById('main');

    if (mainElement) {
      mainElement.scrollTo(0, 0);
    }
  };

  const scrollToPosition = <T extends HTMLElement>(ref?: RefObject<T>) => {
    setTimeout(() => {
      ref?.current?.scrollIntoView({ behavior: 'smooth' });
    }, 100);
  };

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
