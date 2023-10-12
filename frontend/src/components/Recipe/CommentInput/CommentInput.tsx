import { Button, Spacing, Text, Textarea, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import { useToastActionContext } from '@/hooks/context';
import useRecipeCommentMutation from '@/hooks/queries/recipe/useRecipeCommentMutation';

interface CommentInputProps {
  recipeId: number;
}

const MAX_COMMENT_LENGTH = 200;

const CommentInput = ({ recipeId }: CommentInputProps) => {
  const [commentValue, setCommentValue] = useState('');
  const { mutate } = useRecipeCommentMutation(recipeId);

  const theme = useTheme();
  const { toast } = useToastActionContext();

  const handleCommentInput: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    setCommentValue(e.target.value);
  };

  const handleSubmitComment: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    mutate(
      { content: commentValue },
      {
        onSuccess: () => {
          setCommentValue('');
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
    <>
      <CommentForm onSubmit={handleSubmitComment}>
        <CommentTextarea
          placeholder="댓글을 입력하세요. (200자)"
          value={commentValue}
          onChange={handleCommentInput}
          maxLength={MAX_COMMENT_LENGTH}
        />
        <SubmitButton size="xs" customWidth="40px" customHeight="auto" disabled={commentValue.length === 0}>
          등록
        </SubmitButton>
      </CommentForm>
      <Spacing size={8} />
      <Text size="xs" color={theme.textColors.info} align="right">
        {commentValue.length}자 / {MAX_COMMENT_LENGTH}자
      </Text>
    </>
  );
};

export default CommentInput;

const CommentForm = styled.form`
  display: flex;
  gap: 4px;
  justify-content: space-around;
`;

const CommentTextarea = styled(Textarea)`
  width: calc(100% - 50px);
  padding: 8px;
  font-size: 1.4rem;
`;

const SubmitButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.gray2 : theme.colors.primary)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
