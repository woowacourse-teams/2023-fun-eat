import type { ComponentPropsWithoutRef } from 'react';
import styled from 'styled-components';

interface SkeletonProps extends ComponentPropsWithoutRef<'div'> {
  width?: string | number;
  height?: string | number;
}

const Skeleton = ({ width, height }: SkeletonProps) => {
  return <SkeletonContainer width={width} height={height} />;
};

export default Skeleton;

export const SkeletonContainer = styled.div<SkeletonProps>`
  position: absolute;
  width: ${({ width }) => (typeof width === 'number' ? width + 'px' : width)};
  height: ${({ height }) => (typeof height === 'number' ? height + 'px' : height)};
  border-radius: 8px;
  background: linear-gradient(-90deg, #dddddd, #f7f7f7, #dddddd, #f7f7f7);
  background-size: 400%;
  overflow: hidden;
  animation: skeleton-gradient 5s infinite ease-out;

  @keyframes skeleton-gradient {
    0% {
      background-position: 0% 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0% 50%;
    }
  }
`;
