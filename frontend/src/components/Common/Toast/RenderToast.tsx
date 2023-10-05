import { createPortal } from 'react-dom';
import styled from 'styled-components';

import Toast from './Toast';

import useToastContext from '@/hooks/context/useToastContext';

const RenderToast = () => {
  const { toastProps } = useToastContext();

  return createPortal(
    <ToastContainer>
      {toastProps.map((toast) => (
        <Toast key={toast.id} id={toast.id} message={toast.message} isError={toast.isError} />
      ))}
    </ToastContainer>,
    document.body
  );
};

export default RenderToast;

const ToastContainer = styled.div`
  position: fixed;
  width: calc(100% - 40px);
  transform: translate(0, 130%);
`;
