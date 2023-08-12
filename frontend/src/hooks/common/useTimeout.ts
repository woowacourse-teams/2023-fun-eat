import { useCallback, useEffect, useRef } from 'react';

const useTimeout = (fn: Function, ms = 0) => {
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);
  const callbackRef = useRef(fn);

  const timeoutFn = useCallback(() => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }

    timeoutRef.current = setTimeout(() => {
      callbackRef.current();
    }, ms);
  }, [ms]);

  const clear = useCallback(() => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }
  }, []);

  useEffect(() => {
    callbackRef.current = fn;
  }, [fn]);

  useEffect(() => {
    return clear;
  }, []);

  return [timeoutFn, clear];
};

export default useTimeout;
