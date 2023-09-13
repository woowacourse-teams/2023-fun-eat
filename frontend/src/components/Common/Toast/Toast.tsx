import { Text, useTheme } from '@fun-eat/design-system';
import { createPortal } from 'react-dom';
import styled, { css } from 'styled-components';

import { useToast } from '@/hooks/common';
import { fadeOut, slideIn } from '@/styles/animations';

interface ToastProps {
  isError?: boolean;
  message: string;
}

const Toast = ({ isError, message }: ToastProps) => {
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

type ToastStyleProps = Pick<ToastProps, 'isError'>;

const ToastContainer = styled.div<ToastStyleProps & { isAnimating?: boolean }>`
  position: fixed;
  width: calc(100% - 40px);
  max-width: 560px;
  height: 55px;
  border-radius: 10px;
  background: ${({ isError, theme }) => (isError ? theme.colors.error : theme.colors.black)};
  animation: ${({ isAnimating }) =>
    css`
      ${isAnimating ? slideIn : fadeOut} .3s ease-in-out forwards;
    `};
`;

const MessageWrapper = styled(Text)`
  margin-left: 20px;
  line-height: 55px;
`;
