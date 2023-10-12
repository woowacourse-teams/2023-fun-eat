import { Button, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

import { Input } from '@/components/Common';
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

  const handleCommentInput: ChangeEventHandler<HTMLInputElement> = (e) => {
    setCommentValue(e.target.value);
  };

  const handleSubmitComment: FormEventHandler<HTMLFormElement> = async (e) => {
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
            alert(error.message);
            return;
          }

          toast.error('댓글을 등록하는데 오류가 발생했습니다.');
        },
      }
    );
  };

  return (
    <>
      <CommentInputForm onSubmit={handleSubmitComment}>
        <Input
          placeholder="댓글을 입력하세요. (200자)"
          minWidth="90%"
          value={commentValue}
          onChange={handleCommentInput}
        />
        <SubmitButton size="xs" customWidth="40px" disabled={commentValue.length === 0}>
          등록
        </SubmitButton>
      </CommentInputForm>
      <Text size="xs" color={theme.textColors.info} align="right">
        {commentValue.length} / {MAX_COMMENT_LENGTH}
      </Text>
    </>
  );
};

export default CommentInput;

const CommentInputForm = styled.form`
  display: flex;
  gap: 4px;
  justify-content: space-around;
`;

const SubmitButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.gray2 : theme.colors.primary)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
