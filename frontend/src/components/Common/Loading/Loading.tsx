import { Text } from '@fun-eat/design-system';
import styled, { keyframes } from 'styled-components';

import PlateImage from '@/assets/plate.svg';

const DEFAULT_DESCRIPTION = '잠시만 기다려주세요 🥄';

interface LoadingProps {
  customHeight?: string;
  description?: string;
}

const Loading = ({ customHeight = '100%', description = DEFAULT_DESCRIPTION }: LoadingProps) => {
  return (
    <LoadingContainer customHeight={customHeight}>
      <PlateImageWrapper>
        <PlateImage width={120} />
      </PlateImageWrapper>
      <Text align="center">{description}</Text>
    </LoadingContainer>
  );
};

export default Loading;

type LoadingContainerStyleProps = Pick<LoadingProps, 'customHeight'>;

const LoadingContainer = styled.div<LoadingContainerStyleProps>`
  display: flex;
  flex-direction: column;
  row-gap: 36px;
  justify-content: center;
  align-items: center;
  height: ${({ customHeight }) => customHeight};
`;

const rotate = keyframes`
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
`;

const PlateImageWrapper = styled.div`
  animation: ${rotate} 1.5s ease-in-out infinite;
`;
