import { useCallback, useEffect, useRef } from 'react';

// eslint-disable-next-line @typescript-eslint/ban-types
const useDebounce = (fn: Function, ms = 0) => {
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);
  const callbackRef = useRef(fn);

  const debounce = useCallback(() => {
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

  return [debounce, clear];
};

export default useDebounce;
