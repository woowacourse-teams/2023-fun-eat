import { Button } from '@fun-eat/design-system';
import { useState, useEffect } from 'react';
import { styled } from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import { useScroll } from '@/hooks/common';

const ScrollButton = () => {
  const { scrollToTop } = useScroll();
  const [scrollTop, setScrollTop] = useState(false);

  const handleScroll = () => {
    setScrollTop(true);
  };

  useEffect(() => {
    const mainElement = document.getElementById('main');
    if (mainElement) {
      scrollToTop(mainElement);
      setScrollTop(false);
    }
  }, [scrollTop]);

  return (
    <ScrollButtonWrapper customWidth="45px" customHeight="45px" variant="filled" color="gray5" onClick={handleScroll}>
      <SvgIcon variant="triangle" color="white" width={16} height={14} />
    </ScrollButtonWrapper>
  );
};

export default ScrollButton;

const ScrollButtonWrapper = styled(Button)`
  position: fixed;
  bottom: 80px;
  right: 20px;
  border-radius: 50%;

  &:hover {
    transform: scale(1.1);
    transition: all 200ms ease-in-out;
  }

  @media screen and (min-width: 600px) {
    left: calc(50% + 260px);
    transform: translateX(-50%);
  }
`;
