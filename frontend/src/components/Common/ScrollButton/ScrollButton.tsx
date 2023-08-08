import { Button } from '@fun-eat/design-system';
import { styled } from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import useScroll from '@/hooks/useScroll';

const ScrollButton = () => {
  const { scrollToTop } = useScroll();

  return (
    <ScrollButtonWrapper customWidth="45px" customHeight="45px" variant="filled" color="gray5" onClick={scrollToTop}>
      <SvgIcon variant="triangle" color="white" width={16} height={14} />
    </ScrollButtonWrapper>
  );
};

export default ScrollButton;

const ScrollButtonWrapper = styled(Button)`
  position: fixed;
  bottom: 90px;
  right: 32%;
  border-radius: 50%;
  z-index: 1;

  &:hover {
    transform: scale(1.1);
    transition: all 200ms ease-in-out;
  }
`;
