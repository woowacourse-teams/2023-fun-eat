import { Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { Input } from '@/components/Common';
import { useRecipeFormActionContext } from '@/hooks/context';

const MIN_LENGTH = 1;
const MAX_LENGTH = 15;
interface RecipeNameInputProps {
  recipeName: string;
}

const RecipeNameInput = ({ recipeName }: RecipeNameInputProps) => {
  const { handleRecipeFormValue } = useRecipeFormActionContext();
  const theme = useTheme();

  const handleRecipeName: ChangeEventHandler<HTMLInputElement> = (e) => {
    handleRecipeFormValue({ target: 'title', value: e.currentTarget.value });
  };

  return (
    <RecipeNameInputContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        꿀조합 이름
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <RecipeNameStatusText color={theme.textColors.info} tabIndex={0}>
        {recipeName.length}자 / {MAX_LENGTH}자
      </RecipeNameStatusText>
      <Spacing size={12} />
      <Input
        placeholder="재치있는 이름을 지어주세요!"
        value={recipeName}
        onChange={handleRecipeName}
        minLength={MIN_LENGTH}
        maxLength={MAX_LENGTH}
      />
    </RecipeNameInputContainer>
  );
};

export default RecipeNameInput;

const RecipeNameInputContainer = styled.div`
  position: relative;
  width: 300px;
`;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const RecipeNameStatusText = styled(Text)`
  position: absolute;
  top: 0;
  right: 0;
  line-height: 28px;
`;
