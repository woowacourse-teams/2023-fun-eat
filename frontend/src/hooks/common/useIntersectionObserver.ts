import type { RefObject } from 'react';
import { useRef, useEffect } from 'react';

const defaultOptions = {
  root: null,
  rootMargin: '0px',
  threshold: 0.3,
};

const useIntersectionObserver = <T extends HTMLElement>(
  callback: () => void,
  targetRef: RefObject<T>,
  hasNextPage: boolean | undefined
) => {
  const observer = useRef(
    new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          callback();
        }
      });
    }, defaultOptions)
  );

  const observe = (element: T) => {
    observer.current.observe(element);
  };

  const unobserve = (element: T) => {
    observer.current.unobserve(element);
  };

  useEffect(() => {
    if (!targetRef.current) {
      return;
    }

    if (!hasNextPage) {
      unobserve(targetRef.current);
      return;
    }

    observe(targetRef.current);

    return () => {
      observer.current.disconnect();
    };
  }, [targetRef.current, hasNextPage]);
};

export default useIntersectionObserver;
