import { useEffect, useRef, useState } from 'react';

type ToastType = 'error' | 'success';
export interface ToastState {
  id: number;
  message: string;
  type: ToastType;
}

const useToast = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState('');
  const [isAnimating, setIsAnimating] = useState(true);
  const toastTimer = useRef<number | null>(null);

  const showToast = (message: string) => {
    setIsOpen(true);
    setMessage(message);
  };

  const toast = {
    success: (message: string) => showToast(message),
    error: (message: string) => showToast(message),
  };

  useEffect(() => {
    window.setTimeout(() => setIsAnimating(false), 2000);
    if (!isAnimating && toastTimer.current) {
      clearTimeout(toastTimer.current);
    }

    return () => {
      if (toastTimer.current) {
        clearTimeout(toastTimer.current);
      }
    };
  }, [isAnimating]);

  return { isAnimating, isOpen, showToast, toast };
};

export default useToast;
