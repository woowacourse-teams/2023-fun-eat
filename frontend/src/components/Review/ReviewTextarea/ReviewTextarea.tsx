import { Heading, Spacing, Text, Textarea, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

const MAX_LENGTH = 200;

const ReviewTextarea = () => {
  const [reviewValue, setReviewValue] = useState('');
  const theme = useTheme();

  const handleReviewInput: ChangeEventHandler<HTMLTextAreaElement> = (event) => {
    setReviewValue(event.currentTarget.value);
  };

  return (
    <ReviewTextareaContainer>
      <Heading as="h2" size="xl">
        리뷰를 작성해주세요. (200자)
      </Heading>
      <Spacing size={16} />
      <Textarea
        rows={5}
        resize="vertical"
        placeholder=""
        maxLength={MAX_LENGTH}
        value={reviewValue}
        onChange={handleReviewInput}
      />
      <Spacing size={16} />
      <ReviewWritingStatusText color={theme.textColors.info}>
        작성한 글자 수: {reviewValue.length} / {MAX_LENGTH}
      </ReviewWritingStatusText>
    </ReviewTextareaContainer>
  );
};

export default ReviewTextarea;

const ReviewTextareaContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const ReviewWritingStatusText = styled(Text)`
  margin-left: auto;
`;
