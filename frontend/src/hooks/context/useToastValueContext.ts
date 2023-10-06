import { useContext } from 'react';

import { ToastValueContext } from '@/contexts/ToastContext';

const useToastValueContext = () => {
  const toastValue = useContext(ToastValueContext);
  if (toastValue === null || toastValue === undefined) {
    throw new Error('useToastValueContext는 Toast Provider 안에서 사용해야 합니다.');
  }

  return toastValue;
};

export default useToastValueContext;
