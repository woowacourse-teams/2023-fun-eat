import { useEffect, useRef, useState } from 'react';

import { useToastActionContext } from '../context';

const useToast = (id: number) => {
  const { deleteToast } = useToastActionContext();
  const [isShown, setIsShown] = useState(true);

  const showTimeoutRef = useRef<number | null>(null);
  const deleteTimeoutRef = useRef<number | null>(null);

  useEffect(() => {
    showTimeoutRef.current = window.setTimeout(() => setIsShown(false), 2000);

    return () => {
      if (showTimeoutRef.current) {
        clearTimeout(showTimeoutRef.current);
      }
    };
  }, []);

  useEffect(() => {
    if (!isShown) {
      deleteTimeoutRef.current = window.setTimeout(() => deleteToast(id), 2000);
    }

    return () => {
      if (deleteTimeoutRef.current) {
        clearTimeout(deleteTimeoutRef.current);
      }
    };
  }, [isShown]);

  return isShown;
};

export default useToast;
