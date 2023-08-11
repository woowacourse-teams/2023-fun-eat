import type { RefObject } from 'react';

const useScroll = () => {
  const scrollToTop = (mainElement: HTMLElement) => {
    mainElement.scrollTo(0, 0);
  };

  const scrollToPosition = <T extends HTMLElement>(ref?: RefObject<T>) => {
    setTimeout(() => {
      ref?.current?.scrollIntoView({ behavior: 'smooth' });
    }, 100);
  };

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
