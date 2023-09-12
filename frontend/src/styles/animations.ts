import { keyframes } from 'styled-components';

export const slideIn = keyframes`
  0% {
    transform: translateY(-100px);
  }

  100% {
    transform: translateY(70px);
  }
`;

export const fadeOut = keyframes`
  0% {
    transform: translateY(70px);
    opacity: 1;
  }

  100% {
    transform: translateY(70px);
    opacity:0;
  }
`;
