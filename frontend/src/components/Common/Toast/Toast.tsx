import { Text, useTheme } from '@fun-eat/design-system';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

import { useToastActionContext } from '@/hooks/context';
import { fadeOut, slideIn } from '@/styles/animations';

interface ToastProps {
  id: number;
  message: string;
  isError?: boolean;
}

const Toast = ({ id, message, isError = false }: ToastProps) => {
  const theme = useTheme();
  const { deleteToast } = useToastActionContext();
  const [isShown, setIsShown] = useState(true);

  useEffect(() => {
    setTimeout(() => setIsShown(false), 2000);
    if (!isShown) {
      setTimeout(() => deleteToast(id), 500);
    }
  }, [isShown]);

  return (
    <ToastWrapper isError={isError} isAnimating={isShown}>
      <MessageWrapper color={theme.colors.white}>{message}</MessageWrapper>
    </ToastWrapper>
  );
};

export default Toast;

type ToastStyleProps = Pick<ToastProps, 'isError'> & { isAnimating?: boolean };

const ToastWrapper = styled.div<ToastStyleProps>`
  position: relative;
  width: 100%;
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
