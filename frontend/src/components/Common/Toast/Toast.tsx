import { Text, useTheme } from '@fun-eat/design-system';
import { createPortal } from 'react-dom';
import styled from 'styled-components';

import { useToast } from '@/hooks/common';
import { fadeOut, slideIn } from '@/styles/animations';

interface ToastProps {
  isError?: boolean;
  message: string;
}

const Toast = ({ isError = false, message }: ToastProps) => {
  const theme = useTheme();
  const { isAnimating } = useToast();

  return createPortal(
    <ToastContainer isError={isError} isAnimating={isAnimating}>
      <MessageWrapper color={theme.colors.white}>{message}</MessageWrapper>
    </ToastContainer>,
    document.body
  );
};

export default Toast;

type ToastStyleProps = Pick<ToastProps, 'isError'> & { isAnimating?: boolean };

const ToastContainer = styled.div<ToastStyleProps>`
  position: fixed;
  width: calc(100% - 40px);
  height: 55px;
  max-width: 560px;
  border-radius: 10px;
  background: ${({ isError, theme }) => (isError ? theme.colors.error : theme.colors.black)};
  animation: ${({ isAnimating }) => (isAnimating ? slideIn : fadeOut)} 0.3s ease-in-out forwards;
`;

const MessageWrapper = styled(Text)`
  margin-left: 20px;
  line-height: 55px;
`;
