import { Button, Heading, Spacing, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import useStarRating from '@/hooks/useStarRate';

const starList = Array.from({ length: 5 }, (_, index) => index + 1);

const StarRate = () => {
  const { rating, hovering, handleRating, handleMouseEnter, handleMouseLeave } = useStarRating();

  return (
    <StarRateContainer>
      <Heading as="h2" size="xl">
        별점을 남겨주세요.
        <RequiredMark>*</RequiredMark>
      </Heading>
      <Spacing size={20} />
      <div>
        {starList.map((star) => (
          <StarButton
            key={star}
            variant="transparent"
            onClick={() => handleRating(star)}
            onMouseEnter={() => handleMouseEnter(star)}
            onMouseLeave={handleMouseLeave}
          >
            <SvgIconWrapper
              variant="star"
              color={star <= (hovering || rating) ? theme.colors.secondary : theme.colors.gray2}
            />
          </StarButton>
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

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const StarButton = styled(Button)`
  padding: 0 2px;
`;

const SvgIconWrapper = styled(SvgIcon)`
  transition: all 0.3s ease-out;
`;