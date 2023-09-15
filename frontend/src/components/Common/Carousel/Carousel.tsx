import { useEffect, useState } from 'react';
import styled from 'styled-components';

import type { CarouselChildren } from '@/types/common';

interface CarouselProps {
  carouselList: CarouselChildren[];
}

const CAROUSEL_WIDTH = window.innerWidth;

const Carousel = ({ carouselList }: CarouselProps) => {
  const extendedCarouselList = [...carouselList, carouselList[0]];
  const [currentIndex, setCurrentIndex] = useState(0);

  const showNextSlide = () => {
    setCurrentIndex((prev) => (prev === carouselList.length ? 0 : prev + 1));
  };

  useEffect(() => {
    const timer = setInterval(showNextSlide, 2000);

    return () => clearInterval(timer);
  }, [currentIndex]);

  return (
    <CarouselContainer>
      <CarouselWrapper currentIndex={currentIndex}>
        {extendedCarouselList.map(({ id, children }) => (
          <CarouselItem key={id}>{children}</CarouselItem>
        ))}
      </CarouselWrapper>
    </CarouselContainer>
  );
};

export default Carousel;

const CarouselContainer = styled.div`
  display: flex;
  width: 100%;
  border: 1px solid ${({ theme }) => theme.colors.gray2};
  border-radius: 10px;
  overflow: hidden;
`;

const CarouselWrapper = styled.ul<{ currentIndex: number }>`
  display: flex;
  transform: ${({ currentIndex }) => 'translateX(-' + currentIndex * CAROUSEL_WIDTH + 'px)'};
  transition: ${({ currentIndex }) => (currentIndex === length - 1 ? '' : 'all 0.5s ease-in-out')};
`;

const CarouselItem = styled.li`
  width: ${CAROUSEL_WIDTH}px;
  height: fit-content;
`;
