import { useContext } from 'react';

import { ToastActionContext } from '@/contexts/ToastContext';

const useToastActionContext = () => {
  const toastAction = useContext(ToastActionContext);
  if (toastAction === null || toastAction === undefined) {
    throw new Error('useToastActionContext는 Toast Provider 안에서 사용해야 합니다.');
  }

  return toastAction;
};

export default useToastActionContext;
