import type { RefObject } from 'react';

const useScroll = () => {
  const scrollToTop = () => {
    const mainElement = document.getElementById('main');

    if (mainElement) {
      mainElement.scrollTo(0, 0);
    }
  };

  const scrollToPosition = <T extends HTMLElement>(ref?: RefObject<T>) => {
    ref?.current?.scrollIntoView({ behavior: 'smooth' });
  };

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
