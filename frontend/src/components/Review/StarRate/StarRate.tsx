import { Button, Heading, Spacing, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useReviewFormActionContext } from '@/hooks/context';
import { useStarRatingHover } from '@/hooks/review';

const starList = Array.from({ length: 5 }, (_, index) => index + 1);

interface StarRateProps {
  rating: number;
}

const StarRate = ({ rating }: StarRateProps) => {
  const { hovering, handleMouseEnter, handleMouseLeave } = useStarRatingHover();
  const { handleReviewFormValue } = useReviewFormActionContext();

  const handleRating = (star: number) => {
    handleReviewFormValue({ target: 'rating', value: star });
  };

  return (
    <StarRateContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        별점을 남겨주세요.
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={20} />
      <div>
        {starList.map((star) => (
          <Button
            type="button"
            key={star}
            variant="transparent"
            css="padding: 0 2px"
            onClick={() => handleRating(star)}
            onMouseEnter={() => handleMouseEnter(star)}
            onMouseLeave={handleMouseLeave}
            aria-label={`별점 ${star}점`}
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

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const SvgIconWrapper = styled(SvgIcon)`
  transition: all 0.3s ease-out;
`;
