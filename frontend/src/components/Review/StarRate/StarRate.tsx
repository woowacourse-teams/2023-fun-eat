import { Button, Heading, Spacing, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import useStarRating from '@/hooks/useStarRate';

const StarRate = () => {
  const { rating, hovering, handleRating, handleMouseEnter, handleMouseLeave } = useStarRating();

  const starList = Array.from({ length: 5 }, (_, index) => index + 1);

  return (
    <StarRateContainer>
      <Heading as="h2" size="xl">
        별점을 선택해주세요.
      </Heading>
      <Spacing size={16} />
      <div>
        {starList.map((star) => (
          <Button
            key={star}
            color="white"
            variant="filled"
            css="padding: 0 2px"
            onClick={() => handleRating(star)}
            onMouseEnter={() => handleMouseEnter(star)}
            onMouseLeave={handleMouseLeave}
          >
            <SvgIconWrapper
              variant="star"
              color={star <= (hovering || rating) ? theme.colors.secondary : theme.colors.gray2}
            />
          </Button>
        ))}
      </div>
    </StarRateContainer>
  );
};

export default StarRate;

const StarRateContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const SvgIconWrapper = styled(SvgIcon)`
  transition: all 0.3s ease-out;
`;
