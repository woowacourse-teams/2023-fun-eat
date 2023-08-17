import type { ReactNode } from 'react';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

interface CarouselProps {
  carouselList: {
    id: number;
    children: ReactNode;
  }[];
}

const CAROUSEL_WIDTH = 374;

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
  width: ${CAROUSEL_WIDTH}px;
  overflow: hidden;
`;

const CarouselWrapper = styled.ul<{ currentIndex: number }>`
  display: flex;
  transition: ${({ currentIndex }) => (currentIndex === length - 1 ? '' : 'all 0.5s ease-in-out')};
  transform: ${({ currentIndex }) => 'translateX(-' + currentIndex * CAROUSEL_WIDTH + 'px)'};
`;

const CarouselItem = styled.li`
  width: ${CAROUSEL_WIDTH}px;
  height: fit-content;
`;
