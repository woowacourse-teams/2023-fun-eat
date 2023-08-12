import type { DependencyList } from 'react';
import { useEffect } from 'react';

import useTimeout from './useTimeout';

const useDebounce = (fn: Function, ms = 0, deps: DependencyList = []) => {
  const [timeoutFn, clear] = useTimeout(fn, ms);

  useEffect(timeoutFn, deps);

  return clear;
};

export default useDebounce;
