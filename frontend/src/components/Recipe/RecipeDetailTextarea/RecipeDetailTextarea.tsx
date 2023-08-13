import { Heading, Spacing, Textarea, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { useRecipeFormActionContext } from '@/hooks/context';

const MAX_LENGTH = 500;

interface RecipeDetailTextareaProps {
  recipeDetail: string;
}

const RecipeDetailTextarea = ({ recipeDetail }: RecipeDetailTextareaProps) => {
  const theme = useTheme();

  const { handleRecipeFormValue } = useRecipeFormActionContext();

  const handleRecipeDetail: ChangeEventHandler<HTMLTextAreaElement> = (e) => {
    handleRecipeFormValue({ target: 'content', value: e.currentTarget.value });
  };

  return (
    <>
      <Heading as="h2" size="xl" tabIndex={0}>
        자세한 설명을 남겨주세요.
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={12} />
      <Textarea
        rows={5}
        resize="vertical"
        placeholder="만든 꿀조합에 대한 설명을 자유롭게 작성해주세요."
        maxLength={MAX_LENGTH}
        value={recipeDetail}
        onChange={handleRecipeDetail}
      />
      <Spacing size={16} />
      <Text color={theme.textColors.info} size="sm" tabIndex={0}>
        작성한 글자 수: {recipeDetail.length}자 / {MAX_LENGTH}자
      </Text>
    </>
  );
};

export default RecipeDetailTextarea;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;
