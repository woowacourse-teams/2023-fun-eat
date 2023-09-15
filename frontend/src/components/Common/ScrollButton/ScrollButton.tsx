import { Button } from '@fun-eat/design-system';
import type { RefObject } from 'react';
import { useState, useEffect } from 'react';
import { styled } from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { useScroll } from '@/hooks/common';

interface ScrollButtonProps {
  targetRef?: RefObject<HTMLDivElement>;
  isRecipePage?: boolean;
}

const ScrollButton = ({ targetRef, isRecipePage = false }: ScrollButtonProps) => {
  const { scrollToTop } = useScroll();
  const [scrollTop, setScrollTop] = useState(false);

  const handleScroll = () => {
    setScrollTop(true);
  };

  useEffect(() => {
    if (targetRef?.current) {
      scrollToTop(targetRef.current);
      setScrollTop(false);
    }
  }, [scrollTop]);

  return (
    <ScrollButtonWrapper
      isRecipePage={isRecipePage}
      customWidth="45px"
      customHeight="45px"
      variant="filled"
      color="white"
      onClick={handleScroll}
    >
      <SvgIcon variant="arrow" color="gray5" width={16} height={14} />
    </ScrollButtonWrapper>
  );
};

export default ScrollButton;

const ScrollButtonWrapper = styled(Button)<ScrollButtonProps>`
  position: fixed;
  bottom: ${({ isRecipePage }) => (isRecipePage ? '210px' : '90px')};
  right: 20px;
  border-radius: 50%;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 1px 4px;

  @media screen and (min-width: 600px) {
    left: calc(50% + 234px);
  }

  &:hover {
    transform: scale(1.1);
    transition: all 200ms ease-in-out;
  }

  svg {
    rotate: 90deg;
  }
`;
