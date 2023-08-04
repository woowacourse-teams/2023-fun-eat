import { Heading, Spacing, Text, Textarea, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

const MAX_LENGTH = 200;

interface ReviewTextareaProps {
  content: string;
  onReviewInput: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const ReviewTextarea = ({ content, onReviewInput }: ReviewTextareaProps) => {
  const theme = useTheme();

  return (
    <ReviewTextareaContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        리뷰를 남겨주세요.
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={20} />
      <Textarea
        rows={5}
        resize="vertical"
        placeholder="솔직한 리뷰를 써주세요 😊"
        maxLength={MAX_LENGTH}
        value={content}
        onChange={onReviewInput}
      />
      <Spacing size={16} />
      <ReviewWritingStatusText color={theme.textColors.info} tabIndex={0}>
        작성한 글자 수: {content.length}자 / {MAX_LENGTH}자
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

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const ReviewWritingStatusText = styled(Text)`
  margin-left: auto;
`;
