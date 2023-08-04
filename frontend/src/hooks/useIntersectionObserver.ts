import type { RefObject } from 'react';
import { useRef, useEffect } from 'react';

const defaultOptions = {
  root: null,
  rootMargin: '0px',
  threshold: 1.0,
};

const useIntersectionObserver = <T extends HTMLElement>(
  callback: () => void,
  targetRef: RefObject<T>,
  isLastPage: boolean | undefined
) => {
  let isInitial = true;

  const observer = useRef(
    new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          if (isInitial) {
            isInitial = false;
          } else {
            callback();
          }
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

    if (isLastPage) {
      unobserve(targetRef.current);
      return;
    }

    observe(targetRef.current);

    return () => {
      observer.current.disconnect();
    };
  }, [targetRef.current]);
};

export default useIntersectionObserver;
