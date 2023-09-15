import type { RefObject } from 'react';

const useScroll = () => {
  const scrollToTop = <T extends HTMLElement>(ref: RefObject<T>) => {
    if (ref.current) {
      ref.current.scrollTo(0, 0);
    }
  };

  const scrollToPosition = <T extends HTMLElement>(ref: RefObject<T>) => {
    if (ref.current) {
      const timeout = setTimeout(() => {
        ref.current?.scrollIntoView({ behavior: 'smooth' });
        clearTimeout(timeout);
      }, 100);
    }
  };

  return { scrollToTop, scrollToPosition };
};

export default useScroll;
