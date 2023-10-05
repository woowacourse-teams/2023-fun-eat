import { useContext } from 'react';

import { ToastContext } from '@/contexts/ToastContext';

const useToastContext = () => {
  const Toasts = useContext(ToastContext);
  if (Toasts === null || Toasts === undefined) {
    throw new Error('useToastContext는 Toast Provider 안에서 사용해야 합니다.');
  }

  return Toasts;
};

export default useToastContext;
