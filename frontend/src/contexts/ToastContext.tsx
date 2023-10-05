import type { PropsWithChildren, SetStateAction } from 'react';
import { createContext, useEffect, useRef, useState } from 'react';

export interface ToastState {
  id: number;
  message: string;
  isError?: boolean;
}

interface ToastContextProps {
  toast: {
    success: (message: string) => void;
    error: (message: string) => void;
  };
  toastProps: ToastState[];
  setToastProps: React.Dispatch<SetStateAction<ToastState[]>>;
  deleteToast: (id: number) => void;
}

export const ToastContext = createContext<ToastContextProps | null>(null);

const ToastProvider = ({ children }: PropsWithChildren) => {
  const [toastProps, setToastProps] = useState<ToastState[]>([]);

  const showToast = (id: number, message: string, isError?: boolean) => {
    setToastProps([...toastProps, { id, message, isError }]);
  };

  const deleteToast = (id: number) => {
    const toastId = toastProps.findIndex((e) => e.id === id);
    if (toastId === -1) return;

    const newToastItems = [...toastProps];
    newToastItems.splice(toastId, 1);
    setToastProps(newToastItems);
  };

  const toast = {
    success: (message: string) => showToast(Number(Date.now()), message),
    error: (message: string) => showToast(Number(Date.now()), message, true),
  };

  const contextValue = {
    toast,
    toastProps,
    setToastProps,
    deleteToast,
  };

  return <ToastContext.Provider value={contextValue}>{children}</ToastContext.Provider>;
};

export default ToastProvider;
