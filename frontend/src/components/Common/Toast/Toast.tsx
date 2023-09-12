import { Text, useTheme } from '@fun-eat/design-system';
import { createPortal } from 'react-dom';
import styled, { css, keyframes } from 'styled-components';

import { useToast } from '@/hooks/common';

interface ToastProps {
  isOpen: boolean;
  message: string;
}

const Toast = ({ message }: ToastProps) => {
  const theme = useTheme();
  const { isAnimating } = useToast();

  return createPortal(
    <ToastContainer isAnimating={isAnimating}>
      <MessageWrapper color={theme.colors.white}>{message}</MessageWrapper>
    </ToastContainer>,
    document.body
  );
};

export default Toast;

const slideIn = keyframes`
  0% {
    transform: translateY(-100px);
  }

  100% {
    transform: translateY(70px);
  }
`;

const fadeOut = keyframes`
  0% {
    transform: translateY(70px);
    opacity: 1;
  }

  100% {
    transform: translateY(70px);
    opacity:0;
  }
`;

const ToastContainer = styled.div<{ isAnimating?: boolean }>`
  position: fixed;
  width: calc(100% - 40px);
  max-width: 560px;
  height: 55px;
  border-radius: 10px;
  background: ${({ theme }) => theme.colors.black};

  animation: ${({ isAnimating }) =>
    css`
      ${isAnimating ? slideIn : fadeOut} .3s ease-in-out forwards;
    `};
`;

const MessageWrapper = styled(Text)`
  margin-left: 20px;
  line-height: 55px;
`;
