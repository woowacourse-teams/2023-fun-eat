import type { PropsWithChildren, SetStateAction } from 'react';
import { createContext, useState } from 'react';
import { createPortal } from 'react-dom';
import styled from 'styled-components';

import { Toast } from '@/components/Common';

interface ToastState {
  id: number;
  message: string;
  isError?: boolean;
}

interface ToastValue {
  toasts: ToastState[];
}
interface ToastAction {
  toast: {
    success: (message: string) => void;
    error: (message: string) => void;
  };
  setToasts: React.Dispatch<SetStateAction<ToastState[]>>;
  deleteToast: (id: number) => void;
}

export const ToastValueContext = createContext<ToastValue | null>(null);
export const ToastActionContext = createContext<ToastAction | null>(null);

const ToastProvider = ({ children }: PropsWithChildren) => {
  const [toasts, setToasts] = useState<ToastState[]>([]);

  const showToast = (id: number, message: string, isError?: boolean) => {
    setToasts([...toasts, { id, message, isError }]);
  };

  const deleteToast = (id: number) => {
    const toastId = toasts.findIndex((e) => e.id === id);
    if (toastId === -1) return;

    const newToastItems = [...toasts];
    newToastItems.splice(toastId, 1);
    setToasts(newToastItems);
  };

  const toast = {
    success: (message: string) => showToast(Number(Date.now()), message),
    error: (message: string) => showToast(Number(Date.now()), message, true),
  };

  const toastValue = {
    toasts,
  };

  const toastAction = {
    toast,
    setToasts,
    deleteToast,
  };

  return (
    <ToastActionContext.Provider value={toastAction}>
      <ToastValueContext.Provider value={toastValue}>
        {children}
        {createPortal(
          <ToastContainer>
            {toasts.map((toast) => (
              <Toast key={toast.id} id={toast.id} message={toast.message} isError={toast.isError} />
            ))}
          </ToastContainer>,
          document.body
        )}
      </ToastValueContext.Provider>
    </ToastActionContext.Provider>
  );
};

export default ToastProvider;

const ToastContainer = styled.div`
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: calc(100% - 20px);
  transform: translate(0, -10px);
`;
