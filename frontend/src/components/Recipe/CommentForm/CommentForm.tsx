import { Button, Spacing, Text, Textarea, useTheme, useToastActionContext } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler, RefObject } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useScroll } from '@/hooks/common';
import { useRecipeCommentMutation } from '@/hooks/queries/recipe';

interface CommentFormProps {
  recipeId: number;
  scrollTargetRef: RefObject<HTMLElement>;
}

const MAX_COMMENT_LENGTH = 200;

const CommentForm = ({ recipeId, scrollTargetRef }: CommentFormProps) => {
  const [commentValue, setCommentValue] = useState('');
  const { mutate } = useRecipeCommentMutation(recipeId);

  const theme = useTheme();
  const { toast } = useToastActionContext();

  const { scrollToPosition } = useScroll();

  const handleCommentInput: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    setCommentValue(e.target.value);
  };

  const handleSubmitComment: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    mutate(
      { comment: commentValue },
      {
        onSuccess: () => {
          setCommentValue('');
          scrollToPosition(scrollTargetRef);
          toast.success('댓글이 등록되었습니다.');
        },
        onError: (error) => {
          if (error instanceof Error) {
            toast.error(error.message);
            return;
          }

          toast.error('댓글을 등록하는데 오류가 발생했습니다.');
        },
      }
    );
  };

  return (
    <CommentFormContainer>
      <Form onSubmit={handleSubmitComment}>
        <CommentTextarea
          placeholder="댓글을 입력하세요. (200자)"
          value={commentValue}
          onChange={handleCommentInput}
          maxLength={MAX_COMMENT_LENGTH}
        />
        <SubmitButton variant="transparent" disabled={commentValue.length === 0}>
          <SvgIcon
            variant="plane"
            width={30}
            height={30}
            color={commentValue.length === 0 ? theme.colors.gray2 : theme.colors.gray4}
          />
        </SubmitButton>
      </Form>
      <Spacing size={8} />
      <Text size="xs" color={theme.textColors.info} align="right">
        {commentValue.length}자 / {MAX_COMMENT_LENGTH}자
      </Text>
    </CommentFormContainer>
  );
};

export default CommentForm;

const CommentFormContainer = styled.div`
  position: fixed;
  bottom: 0;
  width: calc(100% - 40px);
  max-width: 540px;
  padding: 16px 0;
  background: ${({ theme }) => theme.backgroundColors.default};
`;

const Form = styled.form`
  display: flex;
  gap: 4px;
  justify-content: space-around;
  align-items: center;
`;

const CommentTextarea = styled(Textarea)`
  height: 50px;
  padding: 8px;
  font-size: 1.4rem;
`;

const SubmitButton = styled(Button)`
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
